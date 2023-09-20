package com.esgi.pa.api.dtos.responses.stripe;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record CreatePaymentCommandeResponse(String clientSecret) {
};
