package com.esgi.pa.api.dtos.responses.serviceAbonnement;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * DTO de réponse à une requête de récupération d'informations utilisateur
 *
 * @param id      id numérique de l'utilisateur
 * @param name    nom de l'utilisateur
 * @param email   email de l'utilisateur
 * @param role    rôle de l'utilisateur
 * @param friends amis de l'utilisateur
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record GetServiceAbonnementResponse(
    Long id,
    String name,
    String description,
    Boolean status) {
}
