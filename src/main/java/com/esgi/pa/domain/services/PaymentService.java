package com.esgi.pa.domain.services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esgi.pa.api.dtos.requests.stripe.ItemsRequest;
import com.esgi.pa.api.dtos.requests.stripe.ReceiptPaymentRequest;
import com.esgi.pa.domain.entities.ItemPayment;
import com.esgi.pa.domain.entities.Payment;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.StatusCommandeEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.server.repositories.ItemPaymentRepository;
import com.esgi.pa.server.repositories.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ItemPaymentRepository itemPaymentRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    private static final String UPLOAD_DIR = "src/main/resources/files/";

    public void generateReceiptAsString(ReceiptPaymentRequest paymentRequest, Users users)
            throws IOException, TechnicalFoundException {
        StringBuilder receiptContent = new StringBuilder();

        // Add header information
        receiptContent.append("Payment Receipt\n");
        receiptContent.append("Recipient: ").append(users.getName()).append("\n");
        receiptContent.append("Email: ").append(users.getEmail()).append("\n");
        receiptContent.append("Address: ").append(paymentRequest.numeroRue() + " " + paymentRequest.numeroRueCompl()
                + ", " + paymentRequest.codePostal() + ", " + paymentRequest.ville())
                .append("\n\n");
        receiptContent.append("Tel: ").append("+33 " + paymentRequest.telephone()).append("\n");

        // Add itemized details
        receiptContent.append("Items:\n");
        DecimalFormat df = new DecimalFormat("0.00");
        List<ItemsRequest> items = paymentRequest.items();

        for (ItemsRequest item : items) {
            receiptContent.append("  Name: ").append(item.name()).append("\n");
            receiptContent.append("  Quantity: ").append(item.quantity()).append("\n");
            receiptContent.append("  Price per Unit: $").append(df.format(item.price())).append("\n");
            receiptContent.append("  Item Total: $").append(df.format(item.itemTotal())).append("\n\n");
        }

        // Add total amount
        receiptContent.append("Total Amount: $").append("€ " + df.format(paymentRequest.amount())).append("\n");

        saveReceiptToPdf(receiptContent.toString(), paymentRequest, users);
        receiptContent.setLength(0);

    }

    @Transactional
    public void create(Users users, Integer amount,
            List<ItemsRequest> items, String fileName, String numeroRue,
            String numeroRueCompl, String ville, String codePostal,
            Integer telephone, String noteCommande)
            throws TechnicalFoundException {
        Payment payment = paymentRepository.save(Payment.builder()
                .amount(amount)
                .receiptPath(fileName)
                .numeroRue(numeroRue)
                .numeroRueCompl(numeroRueCompl)
                .ville(ville)
                .codePostal(codePostal)
                .telephone(telephone)
                .noteCommande(noteCommande)
                .users(users)
                .paymentDate(new Date())
                .statusCommande(StatusCommandeEnum.Paid)
                .build());
        items.forEach(el -> {
            itemPaymentRepository.save(ItemPayment.builder()
                    .elementId(el.id())
                    .price(el.price())
                    .quantity(el.quantity())
                    .itemTotal(el.itemTotal())
                    .payment(payment)
                    .type(el.type())

                    .build());
        });
        // Send the receipt to the client via email
        // sendReceiptByEmail(fileName, users);
    }

    public String saveReceiptToPdf(String receiptContent, ReceiptPaymentRequest paymentRequest, Users users)
            throws IOException, TechnicalFoundException {
        // Create a unique file name for the PDF receipt
        String fileName = "receipt_" + UUID.randomUUID().toString();
        String filePath = UPLOAD_DIR + fileName + ".pdf";

        try (PDDocument document = new PDDocument()) { // Initialize the document variable
            PDPage page = new PDPage();
            document.addPage(page);
            receiptContent = receiptContent.replaceAll("\n", " ");

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                String[] lines = receiptContent.split("\n");
                float yOffset = 700; // Initial vertical offset
                float lineHeight = 12; // Height of each line

                for (String line : lines) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(20, yOffset);
                    contentStream.showText(line);
                    contentStream.endText();
                    yOffset -= lineHeight; // Move to the next line
                }
            }

            document.save(filePath);
            if (document != null) {
                document.close();
            }
            create(
                    users,
                    paymentRequest.amount(),
                    paymentRequest.items(),
                    fileName,
                    paymentRequest.numeroRue(),
                    paymentRequest.numeroRueCompl(),
                    paymentRequest.ville(),
                    paymentRequest.codePostal(),
                    paymentRequest.telephone(),
                    paymentRequest.noteCommande());
            return fileName;
        } catch (IOException e) {
            // Handle the exception appropriately
            e.printStackTrace(); // For demonstration; use proper error handling
            return null; // Return an appropriate value or handle the error accordingly
        }
    }

    public void sendReceiptByEmail(String fileName, Users users) throws MailException, javax.mail.MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("Cooking-APP <koumwinnie@zohomail.com>");
            helper.setTo(users.getEmail());
            helper.setSubject("Payment Receipt");
            helper.setText("Hello " + users.getName() + ",\nPlease find your payment receipt attached.\nSincerely");

            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Resource pdfFile = new UrlResource(filePath.toUri());

            // Attach the PDF file to the email
            helper.addAttachment("receipt.pdf", pdfFile.getFile());

            javaMailSender.send(message);
            System.out.println("Email with attachment sent successfully!");
        } catch (MailException | MessagingException | IOException e) {
            System.err.println("Failed to send email with attachment: " + e.getMessage());
        }

    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }
}
