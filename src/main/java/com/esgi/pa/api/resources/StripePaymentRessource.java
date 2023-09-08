package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.requests.stripe.CheckPaymentRequest;
import com.esgi.pa.api.dtos.requests.stripe.ReceiptPaymentRequest;
import com.esgi.pa.api.dtos.responses.stripe.CreatePaymentResponse;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.PaymentService;
import com.esgi.pa.domain.services.UserService;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Validated
@RequiredArgsConstructor
@RestController
@Api(tags = "Stripe API")
@RequestMapping("/payment")
public class StripePaymentRessource {

  @Value("${stripe.secretKey}") // Define your Stripe secret API key in application.properties
  private String stripeSecretKey;

  private final UserService userService;
  private final PaymentService paymentService;

  @PostMapping("/create-payment-intent/{id}")
  @ResponseBody
  public CreatePaymentResponse checkpaiementStripe(
      @Valid @RequestBody CheckPaymentRequest request) throws Exception {
    Stripe.apiKey = stripeSecretKey;
    // Create PaymentIntent
    PaymentIntentCreateParams params = PaymentIntentCreateParams
        .builder()
        .setAmount((long) request.amount() * 100)
        .setCurrency("eur")
        .setAutomaticPaymentMethods(
            PaymentIntentCreateParams.AutomaticPaymentMethods
                .builder()
                .setEnabled(true)
                .build())
        .build();

    // Create a PaymentIntent with the order amount and currency
    PaymentIntent paymentIntent = PaymentIntent.create(params);
    return new CreatePaymentResponse(paymentIntent.getClientSecret());
  }

  @PostMapping(value = "/generate/{id}")
  @ResponseStatus(CREATED)
  public ResponseEntity<String> generateReceipt(
      @Valid @RequestBody ReceiptPaymentRequest request,
      @PathVariable Long id)
      throws TechnicalFoundException, TechnicalNotFoundException, IOException, MessagingException {
    Users users = userService.getById(id);
    // Generate the receipt as a string
   paymentService.generateReceiptAsString(request, users);
    // Save the receipt to the resources directory

    return ResponseEntity.ok("Receipt generated, sent, and saved successfully.");
  }
}
