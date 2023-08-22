package com.esgi.pa.api.dtos.requests.serviceAbonnement;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import javax.validation.constraints.NotBlank;
import lombok.Builder;

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
  @NotBlank(message = "Long is required") Long id,
  @NotBlank(message = "Name is required") String name,
  String description,
  @NotBlank(message = "imgPath is required") String imgPath,
  Boolean status
) {}
