package com.esgi.pa.api.dtos.requests.categorieMateriel;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UpdateCategorieMaterielRequest(
  @NotNull(message = "Long is required") Long id,
  @NotBlank(message = "Name is required") String name
) {}
