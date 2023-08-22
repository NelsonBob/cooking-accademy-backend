package com.esgi.pa.api.dtos.responses.serviceAbonnement;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetServiceAbonnementResponse(
    Long id,
    String name,
    String description,
    Boolean status) {
}
