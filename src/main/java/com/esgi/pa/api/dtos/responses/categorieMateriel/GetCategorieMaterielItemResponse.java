package com.esgi.pa.api.dtos.responses.categorieMateriel;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetCategorieMaterielItemResponse(
    Long id,
    String name) {
}
