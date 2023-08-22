package com.esgi.pa.api.dtos.requests.optionServiceAbonnement;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import javax.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * DTO de requête de création de compte
 *
 * @param name     nom de l'utilisateur
 * @param description    email de l'utilisateur
 */
@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record CreateOptionServiceAbonnementRequest(
  @NotBlank(message = "Name is required") Long serviceAbonnement,
  @NotBlank(message = "Icon is required") Boolean icon,
  @NotBlank(message = "Description is required") Boolean description,
  @NotBlank(message = "ValueIcon is required") String valueicon,
  @NotBlank(message = "Description is required") String descriptionvalue
) {}
