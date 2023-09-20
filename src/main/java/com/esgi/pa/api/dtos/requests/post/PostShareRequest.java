package com.esgi.pa.api.dtos.requests.post;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record PostShareRequest(
  @NotNull(message = "PostId is required") Long id,
  @NotBlank(message = "datepost is required") String datepost
) {}
