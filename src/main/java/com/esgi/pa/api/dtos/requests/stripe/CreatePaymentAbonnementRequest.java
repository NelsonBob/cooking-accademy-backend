package com.esgi.pa.api.dtos.requests.stripe;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.esgi.pa.domain.enums.TypeAbonnement;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record CreatePaymentAbonnementRequest(
  @NotBlank(message = "amount is required") double amount,
  @NotBlank(message = "typeAbonnement is required")
  TypeAbonnement typeAbonnement,
  @NotNull(message = "id service is required") Long service
) {}
