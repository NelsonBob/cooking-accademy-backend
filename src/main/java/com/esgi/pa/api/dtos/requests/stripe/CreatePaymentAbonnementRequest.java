package com.esgi.pa.api.dtos.requests.stripe;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotNull;

import com.esgi.pa.domain.enums.TypeAbonnement;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record CreatePaymentAbonnementRequest(
    @NotNull(message = "amount is required") Double amount,
    @NotNull(message = "typeAbonnement is required") TypeAbonnement typeAbonnement,
    @NotNull(message = "id service is required") Long service) {
}
