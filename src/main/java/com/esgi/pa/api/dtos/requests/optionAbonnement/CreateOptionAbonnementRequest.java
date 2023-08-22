package com.esgi.pa.api.dtos.requests.optionAbonnement;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.api.dtos.requests.optionServiceAbonnement.CreateOptionServiceAbonnementRequest;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.List;
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
public record CreateOptionAbonnementRequest(
  @NotBlank(message = "Name is required") String name,
  @NotNull(message = "optionServiceAbonnementRequests is required")
  List<CreateOptionServiceAbonnementRequest> optionServiceAbonnementRequests
) {}
