package com.esgi.pa.api.dtos.requests.categorieMateriel;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import javax.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record CreateCategorieMaterielRequest(
  @NotBlank(message = "Name is required") String name
) {}
