package com.esgi.pa.api.dtos.responses.repas;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetRepasResponse(
    Long id,
    String name,
    String description,
    String imgPath,
    Integer quantity,
    Float price,
    Boolean status) {
}
