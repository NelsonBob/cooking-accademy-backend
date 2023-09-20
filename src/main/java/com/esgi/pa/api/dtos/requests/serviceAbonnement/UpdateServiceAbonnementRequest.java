package com.esgi.pa.api.dtos.requests.serviceAbonnement;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UpdateServiceAbonnementRequest(
  @NotNull(message = "Long is required") Long id,
  @NotBlank(message = "Name is required") String name,
  String description,
  @NotBlank(message = "imgPath is required") String imgPath,
  Boolean status,
  Boolean isDefault
) {}
