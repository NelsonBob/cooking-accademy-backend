package com.esgi.pa.api.dtos.requests.stripe;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotNull;

import com.esgi.pa.domain.enums.StatusCommandeEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record ConfirmLivraionPaymentRequest(
        @NotNull(message = "id is required") Long id,
        @NotNull(message = "type is required") StatusCommandeEnum type) {
}
