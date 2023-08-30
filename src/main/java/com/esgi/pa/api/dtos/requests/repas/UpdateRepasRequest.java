package com.esgi.pa.api.dtos.requests.repas;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UpdateRepasRequest(
        @NotNull(message = "Long is required") Long id,
        @NotBlank(message = "Name is required") String name,
        String description,
        @NotBlank(message = "imgPath is required") String imgPath,
        @Min(value = 1, message = "Quantity must be at least 1") Integer quantity,
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be a positive value") Float price) {
}
