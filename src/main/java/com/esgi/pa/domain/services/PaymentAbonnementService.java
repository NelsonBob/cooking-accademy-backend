package com.esgi.pa.domain.services;

import com.esgi.pa.api.dtos.requests.stripe.CreatePaymentAbonnementRequest;
import com.esgi.pa.domain.entities.PaymentAbonnement;
import com.esgi.pa.domain.entities.ServiceAbonnement;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.TypeAbonnement;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.PaymentAbonnementRepository;
import com.esgi.pa.server.repositories.UsersRepository;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentAbonnementService {

  private final PaymentAbonnementRepository paymentAbonnementRepository;
  private final UsersRepository usersRepository;
  private final UserService userService;

  private static final String UPLOAD_DIR = "src/main/resources/files/";

  public void generateReceiptAsString(
    CreatePaymentAbonnementRequest paymentRequest,
    Users users,
    ServiceAbonnement serviceAbonnement
  )
    throws IOException, TechnicalFoundException, MailException, MessagingException {
    StringBuilder receiptContent = new StringBuilder();

    // Add header information
    receiptContent.append("Payment Receipt\n");
    receiptContent.append("Recipient: ").append(users.getName()).append("\n");
    receiptContent.append("Email: ").append(users.getEmail()).append("\n");
    receiptContent
      .append("Abonnement: ")
      .append(paymentRequest.amount() + " " + paymentRequest.typeAbonnement())
      .append("\n\n");
    saveReceiptToPdf(
      receiptContent.toString(),
      paymentRequest,
      users,
      serviceAbonnement
    );
    receiptContent.setLength(0);
  }

  public String saveReceiptToPdf(
    String receiptContent,
    CreatePaymentAbonnementRequest paymentRequest,
    Users users,
    ServiceAbonnement serviceAbonnement
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
        paymentRequest.typeAbonnement(),
        fileName,
        serviceAbonnement
      );
      return fileName;
    } catch (IOException e) {
      // Handle the exception appropriately
      e.printStackTrace(); // For demonstration; use proper error handling
      return null; // Return an appropriate value or handle the error accordingly
    }
  }

  @Transactional
  public void create(
    Users users,
    double amount,
    TypeAbonnement typeAbonnement,
    String fileName,
    ServiceAbonnement serviceAbonnement
  ) throws TechnicalFoundException {
    paymentAbonnementRepository.save(
      PaymentAbonnement
        .builder()
        .amount(amount)
        .receiptPath(fileName)
        .users(users)
        .paymentDate(new Date())
        .build()
    );

    users.setServiceAbonnement(serviceAbonnement);
    users.setDateSuscription(new Date());
    users.setDateExpiration(userService.dateSubscription(typeAbonnement));
    usersRepository.save(users);
  }

  public List<PaymentAbonnement> findAll() {
    return paymentAbonnementRepository.findAll();
  }

  public List<PaymentAbonnement> listPaymentUser(Users users) {
    return paymentAbonnementRepository.findByUsers(users);
  }

  public PaymentAbonnement getById(Long id) throws TechnicalNotFoundException {
    return paymentAbonnementRepository
      .findById(id)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No payment found with following id : " + id
        )
      );
  }
}
