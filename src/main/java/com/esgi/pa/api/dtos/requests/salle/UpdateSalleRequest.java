package com.esgi.pa.api.dtos.requests.salle;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.List;

import com.esgi.pa.api.dtos.requests.gallerie.GallerieRequest;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UpdateSalleRequest(
  @NotBlank(message = "name is required") String name,
  @NotNull(message = "Id is required") Long id,
  @NotBlank(message = "Description is required") String description,
  @NotBlank(message = "imgPath is required") String imgPath,
  @NotNull(message = "gallerie is required") List<GallerieRequest> gallerie,
  @NotNull(message = "status is required") Boolean status
) {}
