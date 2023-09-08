package com.esgi.pa.api.dtos.responses.stripe;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.domain.enums.TypeCommandeEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record ItemPaymentResponse(Long id, Integer price,
    Integer itemTotal, Integer quantity, TypeCommandeEnum type) {
}
