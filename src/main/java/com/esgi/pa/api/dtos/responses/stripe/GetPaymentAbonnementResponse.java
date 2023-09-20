package com.esgi.pa.api.dtos.responses.stripe;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.Date;

import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.esgi.pa.domain.enums.TypeAbonnement;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetPaymentAbonnementResponse(
  Long id,
  double amount,
  GetUserResponse user,
  TypeAbonnement typeAbonnement,
  String paymentDate,
  String receiptPath,
  Date dateSuscription,
  Date dateExpiration
) {}
