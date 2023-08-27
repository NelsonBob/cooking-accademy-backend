package com.esgi.pa.api.dtos.requests.optionServiceAbonnement;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;

/**
 * DTO de requête de création de compte
 *
 * @param name     nom de l'utilisateur
 * @param description    email de l'utilisateur
 */
@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UpdateOptionServiceAbonnementRequest(
  @NotNull(message = "Long is required") Long id,
  @NotBlank(message = "Name is required") Long serviceAbonnement,
  @NotBlank(message = "Icon is required") Boolean icon,
  @NotBlank(message = "Description is required") Boolean description,
  @NotBlank(message = "ValueIcon is required") Boolean isValueicon,
  @NotBlank(message = "DescriptionValue is required") String descriptionvalue
) {}
