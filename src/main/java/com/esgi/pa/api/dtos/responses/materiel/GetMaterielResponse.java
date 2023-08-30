package com.esgi.pa.api.dtos.responses.materiel;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.api.dtos.responses.categorieMateriel.GetCategorieMaterielItemResponse;
import com.esgi.pa.api.dtos.responses.intern.GetInternResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetMaterielResponse(
    Long id,
    String name,
    String description,
    String imgPath,
    Boolean status,
    String[] gallerie,
    Integer quantity,
    Float price,
    GetCategorieMaterielItemResponse categorieMateriel,
    GetInternResponse creator) {
}
