package com.esgi.pa.api.dtos.requests.serviceAbonnement;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UpdateServiceAbonnementRequest(
  @NotNull(message = "Long is required") Long id,
  @NotBlank(message = "Name is required") String name,
  String description,
  @NotBlank(message = "imgPath is required") String imgPath,
  Boolean status,
  @NotNull(message = "isDefault is required") Boolean isDefault
) {}
