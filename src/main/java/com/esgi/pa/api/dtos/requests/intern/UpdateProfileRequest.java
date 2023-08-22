package com.esgi.pa.api.dtos.requests.intern;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

/**
 * DTO de requête de création de compte
 *
 * @param name     nom de l'utilisateur
 * @param fonction     role de l'utilisateur
 */
@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UpdateProfileRequest(
    @NotBlank(message = "Name is required")
    String name,
    @NotBlank(message = "Fonction is required")
    String fonction
) {

}
