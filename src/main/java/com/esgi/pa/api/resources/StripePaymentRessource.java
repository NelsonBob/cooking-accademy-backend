package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.requests.stripe.AssignLivreurPaymentRequest;
import com.esgi.pa.api.dtos.requests.stripe.CheckPaymentRequest;
import com.esgi.pa.api.dtos.requests.stripe.ConfirmLivraionPaymentRequest;
import com.esgi.pa.api.dtos.requests.stripe.ReceiptPaymentRequest;
import com.esgi.pa.api.dtos.responses.stripe.CreatePaymentResponse;
import com.esgi.pa.api.dtos.responses.stripe.GetPaymentResponse;
import com.esgi.pa.api.mappers.PaymentMapper;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Payment;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.exceptions.NotAuthorizationRessourceException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.InternService;
import com.esgi.pa.domain.services.PaymentService;
import com.esgi.pa.domain.services.UserService;
import com.esgi.pa.domain.services.util.UtilService;
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
  private final InternService internService;

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

  @GetMapping("{id}")
  public List<GetPaymentResponse> getAllPayment(@PathVariable Long id)
      throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (UtilService.isGranted(users.getRole(), Arrays.asList(RoleEnum.Admin)))
      return PaymentMapper.toGetPaymentResponse(paymentService.findAll());
    else if (UtilService.isGranted(users.getRole(), Arrays.asList(RoleEnum.Livreur))) {
      Intern intern = internService.getById(users);
      return PaymentMapper.toGetPaymentResponse(paymentService.listPaymentLivreur(intern));
    } else
      return PaymentMapper.toGetPaymentResponse(paymentService.listPaymentUser(users));

  }

  @PutMapping("assign-livreur/{id}")
  @ResponseStatus(OK)
  public void assignLivreur(
      @Valid @RequestBody AssignLivreurPaymentRequest request,
      @PathVariable Long id)
      throws TechnicalFoundException, TechnicalNotFoundException,
      NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      Users users1 = userService.getById(request.livreur());
      Intern intern = internService.getById(users1);
      Payment payment = paymentService.getById(request.id());
      paymentService.assignLivreur(intern, payment);
    } else
      throw new NotAuthorizationRessourceException(
          "Vous n'etes pas authorisé à accéder à cette ressource");
  }

  @PutMapping(value = "valided-livraison/{id}")
  @ResponseStatus(OK)
  public void confirmLivraison(
      @Valid @RequestBody ConfirmLivraionPaymentRequest request,
      @PathVariable Long id)
      throws TechnicalFoundException, TechnicalNotFoundException,
      NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      Payment payment = paymentService.getById(request.id());
      paymentService.validateLivraison(request.type(), payment);
    } else
      throw new NotAuthorizationRessourceException(
          "Vous n'etes pas authorisé à accéder à cette ressource");
  }
}
