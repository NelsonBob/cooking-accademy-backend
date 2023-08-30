package com.esgi.pa.api.dtos.requests.materiel;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.esgi.pa.api.dtos.requests.gallerie.GallerieRequest;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record CreateMaterielRequest(
        @NotBlank(message = "name is required") String name,
        @NotBlank(message = "Description is required") String description,
        @NotBlank(message = "imgPath is required") String imgPath,
        @Min(value = 1, message = "Quantity must be at least 1") Integer quantity,
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be a positive value") Float price,
        @NotNull(message = "categorie is required") Long categorieMateriel,
        @NotNull(message = "gallerie is required") List<GallerieRequest> gallerie) {
}
