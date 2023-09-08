package com.esgi.pa.api.dtos.requests.stripe;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record AssignLivreurPaymentRequest(
        @NotNull(message = "id is required") Long id,
        @NotNull(message = "livreur is required") Long livreur) {
}
