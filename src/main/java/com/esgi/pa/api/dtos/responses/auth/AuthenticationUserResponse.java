package com.esgi.pa.api.dtos.responses.auth;

/**
 * DTO de réponse d'authentification d'un utilisateur
 *
 * @param token JWT identifiant l'utilisateur
 */
public record AuthenticationUserResponse(String token) {
}
