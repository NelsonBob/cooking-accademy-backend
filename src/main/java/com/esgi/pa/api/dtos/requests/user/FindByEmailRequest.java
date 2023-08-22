package com.esgi.pa.api.dtos.requests.user;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

/**
 * DTO de requête de création de compte
 *
 * @param email    email de l'utilisateur
 */
@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record FindByEmailRequest(
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email
) {

}
