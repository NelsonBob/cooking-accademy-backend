package com.esgi.pa.api.dtos.requests.serviceAbonnement;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

/**
 * DTO de requête de création de compte
 *
 * @param name     nom de l'utilisateur
 * @param description    email de l'utilisateur
 */
@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record CreateServiceAbonnementRequest(
    @NotBlank(message = "Name is required")
    String name,
    String description,
    String imgPath
) {

}
