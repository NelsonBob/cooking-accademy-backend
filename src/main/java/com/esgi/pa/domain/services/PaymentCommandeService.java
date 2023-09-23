package com.esgi.pa.domain.services;

import com.esgi.pa.api.dtos.requests.stripe.ItemsRequest;
import com.esgi.pa.api.dtos.requests.stripe.ReceiptPaymentRequest;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.ItemPayment;
import com.esgi.pa.domain.entities.PaymentCommande;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.StatusCommandeEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.ItemPaymentRepository;
import com.esgi.pa.server.repositories.PaymentCommandeRepository;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentCommandeService {

  private final PaymentCommandeRepository paymentRepository;
  private final ItemPaymentRepository itemPaymentRepository;

  @Value("${application.base.url.front}")
  private String baseFront;

  @Autowired
  private JavaMailSender javaMailSender;

  private static final String UPLOAD_DIR = "src/main/resources/files/";

  public void generateReceiptAsString(
    ReceiptPaymentRequest paymentRequest,
    Users users
  )
    throws IOException, TechnicalFoundException, MailException, MessagingException {
    StringBuilder receiptContent = new StringBuilder();

    // Add header information
    receiptContent.append("Payment Receipt\n");
    receiptContent.append("Recipient: ").append(users.getName()).append("\n");
    receiptContent.append("Email: ").append(users.getEmail()).append("\n");
    receiptContent
      .append("Address: ")
      .append(
        paymentRequest.numeroRue() +
        " " +
        paymentRequest.numeroRueCompl() +
        ", " +
        paymentRequest.codePostal() +
        ", " +
        paymentRequest.ville()
      )
      .append("\n\n");
    receiptContent
      .append("Tel: ")
      .append("+33 " + paymentRequest.telephone())
      .append("\n");

    // Add itemized details
    receiptContent.append("Items:\n");
    DecimalFormat df = new DecimalFormat("0.00");
    List<ItemsRequest> items = paymentRequest.items();

    for (ItemsRequest item : items) {
      receiptContent.append("  Name: ").append(item.name()).append("\n");
      receiptContent
        .append("  Quantity: ")
        .append(item.quantity())
        .append("\n");
      receiptContent
        .append("  Price per Unit: $")
        .append(df.format(item.price()))
        .append("\n");
      receiptContent
        .append("  Item Total: $")
        .append(df.format(item.itemTotal()))
        .append("\n\n");
    }

    // Add total amount
    receiptContent
      .append("Total Amount: $")
      .append("€ " + df.format(paymentRequest.amount()))
      .append("\n");

    saveReceiptToPdf(receiptContent.toString(), paymentRequest, users);
    receiptContent.setLength(0);
  }

  @Transactional
  public void create(
    Users users,
    Integer amount,
    List<ItemsRequest> items,
    String fileName,
    String numeroRue,
    String numeroRueCompl,
    String ville,
    String codePostal,
    Integer telephone,
    String noteCommande
  ) throws TechnicalFoundException, MailException, MessagingException {
    PaymentCommande payment = paymentRepository.save(
      PaymentCommande
        .builder()
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
        .build()
    );
    items.forEach(el -> {
      itemPaymentRepository.save(
        ItemPayment
          .builder()
          .elementId(el.id())
          .price(el.price())
          .quantity(el.quantity())
          .itemTotal(el.itemTotal())
          .payment(payment)
          .type(el.type())
          .name(el.name())
          .build()
      );
    });
    // Send the receipt to the client via email
    sendReceiptByEmail(fileName, users);
  }

  public String saveReceiptToPdf(
    String receiptContent,
    ReceiptPaymentRequest paymentRequest,
    Users users
  )
    throws IOException, TechnicalFoundException, MailException, MessagingException {
    // Create a unique file name for the PDF receipt
    String fileName = "receipt_" + UUID.randomUUID().toString() + ".pdf";
    String filePath = UPLOAD_DIR + fileName;

    try (PDDocument document = new PDDocument()) { // Initialize the document variable
      PDPage page = new PDPage();
      document.addPage(page);
      receiptContent = receiptContent.replaceAll("\n", " ");

      try (
        PDPageContentStream contentStream = new PDPageContentStream(
          document,
          page
        )
      ) {
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
        paymentRequest.noteCommande()
      );
      return fileName;
    } catch (IOException e) {
      // Handle the exception appropriately
      e.printStackTrace(); // For demonstration; use proper error handling
      return null; // Return an appropriate value or handle the error accordingly
    }
  }

  public void sendReceiptByEmail(String fileName, Users users)
    throws MailException, MessagingException {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setFrom("Cooking-APP <koumwinnie@zohomail.com>");
      helper.setTo(users.getEmail());
      helper.setSubject("Payment Receipt");
      helper.setText(
        "Hello " +
        users.getName() +
        ",\nPlease find your payment receipt attached.\nSincerely"
      );

      Path filePath = Paths.get(UPLOAD_DIR + fileName);
      Resource pdfFile = new UrlResource(filePath.toUri());

      // Attach the PDF file to the email
      helper.addAttachment("receipt.pdf", pdfFile.getFile());

      javaMailSender.send(message);
      System.out.println("Email with attachment sent successfully!");
    } catch (MailException | MessagingException | IOException e) {
      System.err.println(
        "Failed to send email with attachment: " + e.getMessage()
      );
    }
  }

  public List<PaymentCommande> findAll() {
    return paymentRepository.findAll();
  }

  public List<PaymentCommande> listPaymentLivreur(Intern livreur) {
    return paymentRepository.findByLivreur(livreur);
  }

  public List<PaymentCommande> listPaymentUser(Users users) {
    return paymentRepository.findByUsers(users);
  }

  public PaymentCommande getById(Long id) throws TechnicalNotFoundException {
    return paymentRepository
      .findById(id)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No payment found with following id : " + id
        )
      );
  }

  public void assignLivreur(Intern intern, PaymentCommande payment)
    throws TechnicalNotFoundException {
    payment.setLivreur(intern);
    paymentRepository.save(payment);
  }

  public void sendAvisByEmail(Users users, long idpay)
    throws MailException, MessagingException {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setFrom("Cooking-APP <koumwinnie@zohomail.com>");
      helper.setTo(users.getEmail());
      helper.setSubject("Delivery colis");
      helper.setText(
        "<html>" +
        "<body>" +
        "<p>Hello " +
        users.getName() +
        ",</p>" +
        "<p>Your parcel has been delivered.</p>" +
        "<p>Please <a href='" +
        baseFront +
        "?id=" +
        idpay +
        "'>click here</a> to leave your feedback.</p>" +
        "<p>Sincerely</p>" +
        "</body>" +
        "</html>",
        true
      );

      javaMailSender.send(message);
      System.out.println("Email sent successfully!");
    } catch (MailException | MessagingException e) {
      System.err.println(
        "Failed to send email with attachment: " + e.getMessage()
      );
    }
  }

  public void validateLivraison(
    StatusCommandeEnum statusCommandeEnum,
    PaymentCommande payment
  ) throws TechnicalNotFoundException, MailException, MessagingException {
    payment.setStatusCommande(statusCommandeEnum);
    paymentRepository.save(payment);

    if (statusCommandeEnum == StatusCommandeEnum.Delivered) {
      sendAvisByEmail(payment.getUsers(), payment.getId());
    }
  }
}
