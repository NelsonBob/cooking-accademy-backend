package com.esgi.pa.api.dtos.requests.stripe;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record CheckPaymentRequest(
        @NotNull(message = "Amount is required") Integer amount) {
}
