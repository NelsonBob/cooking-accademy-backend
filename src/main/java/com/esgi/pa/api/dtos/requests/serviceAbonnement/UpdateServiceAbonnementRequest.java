package com.esgi.pa.api.dtos.requests.serviceAbonnement;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de requête de création de compte
 *
 * @param name     nom
 * @param id     oid
 * @param description    email de l'utilisateur
 * @param status mot de passe de l'utilisateur
 */
@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UpdateServiceAbonnementRequest(
    @NotBlank(message = "Long is required")
    Long id,
    @NotBlank(message = "Name is required")
    String name,
    String description,
    String imgPath,
    Boolean status
) {

}
