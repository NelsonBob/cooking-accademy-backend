package com.esgi.pa.api.dtos.requests.stripe;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record ReceiptPaymentRequest(
        @NotNull(message = "Amount is required") Integer amount,
        @NotNull(message = "numeroRue is required") String numeroRue,
        String numeroRueCompl,
        @NotNull(message = "ville is required") String ville,
        @NotNull(message = "codePostal is required") String codePostal,
        @NotNull(message = "telephone is required") Integer telephone,
        String noteCommande,
        @NotNull(message = "Item is required") List<ItemsRequest> items) {
}
