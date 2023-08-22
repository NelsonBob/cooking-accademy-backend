package com.esgi.pa.api.dtos.requests.salle;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import javax.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UpdateSalleRequest(
  @NotBlank(message = "name is required") String name,
  @NotBlank(message = "Id is required") Long id,
  @NotBlank(message = "Description is required") String description,
  @NotBlank(message = "imgPath is required") String imgPath,
  @NotBlank(message = "gallerie is required") String gallerie,
  @NotBlank(message = "status is required") Boolean status
) {}
