package com.esgi.pa.api.dtos.requests.optionAbonnement;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.esgi.pa.api.dtos.requests.optionServiceAbonnement.UpdateOptionServiceAbonnementRequest;
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
public record UpdateOptionAbonnementRequest(
  @NotBlank(message = "Long is required") Long id,
  @NotBlank(message = "Name is required") String name,
  @NotBlank(message = "Status is required") Boolean status,
  List<UpdateOptionServiceAbonnementRequest> optionServiceAbonnementRequests
) {}
