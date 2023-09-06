package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.stripe.ValidatePayementRequest;
import com.esgi.pa.api.dtos.responses.stripe.CreatePaymentResponse;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.services.UserService;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import io.swagger.annotations.Api;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@Api(tags = "Stripe API")
@RequestMapping("/payment")
public class StripePayementRessource {

  @Value("${stripe.secretKey}") // Define your Stripe secret API key in application.properties
  private String stripeSecretKey;

  private final UserService userService;

  @PostMapping("/create-payment-intent/{id}")
  @ResponseBody
  public CreatePaymentResponse paymentCreate(
    @PathVariable Long id,
    @Valid @RequestBody ValidatePayementRequest request
  ) throws Exception {
    Stripe.apiKey = stripeSecretKey;
    Users users = userService.getById(id);
    // Create PaymentIntent
    PaymentIntentCreateParams params = PaymentIntentCreateParams
      .builder()
      .setAmount((long) request.amount() * 100)
      .setCurrency("eur")
      .setAutomaticPaymentMethods(
        PaymentIntentCreateParams.AutomaticPaymentMethods
          .builder()
          .setEnabled(true)
          .build()
      )
      .build();

    // Create a PaymentIntent with the order amount and currency
    PaymentIntent paymentIntent = PaymentIntent.create(params);
    return new CreatePaymentResponse(paymentIntent.getClientSecret());
  }
}
