package com.esgi.pa.api.dtos.requests.cour;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UpdateCourRequest(
  @NotNull(message = "Long is required") Long id,
  Boolean status,
  Boolean isVideoLocal,
  @NotBlank(message = "Name is required") String name,
  @NotBlank(message = "description is required") String description,
  @NotBlank(message = "imgPath is required") String imgPath,
  @NotBlank(message = "videoLink is required") String videoLink,
  @NotBlank(message = "contentCour is required") String contentCour
) {}
