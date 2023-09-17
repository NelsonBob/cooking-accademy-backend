package com.esgi.pa.api.dtos.requests.post;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record PostAddRequest(
  @NotNull(message = "user is required") Long user,
  @NotBlank(message = "description is required") String description,
  @NotBlank(message = "imgPath is required") String imgPath,
  @NotBlank(message = "datepost is required") String datepost
) {}
