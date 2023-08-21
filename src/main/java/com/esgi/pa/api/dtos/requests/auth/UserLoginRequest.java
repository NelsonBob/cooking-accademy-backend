package com.esgi.pa.api.dtos.requests.auth;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de requête pour la connexion d'un utilisateur
 *
 * @param email    email de l'utilisateur
 * @param password mot de passe de l'utilisateur
 */
@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UserLoginRequest(
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,
    @NotBlank(message = "Password is required")
    String password
) {

}
