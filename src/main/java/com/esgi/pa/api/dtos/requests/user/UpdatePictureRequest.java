package com.esgi.pa.api.dtos.requests.user;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UpdatePictureRequest(
  @NotBlank(message = "imgPath is required") String imgPath
) {}
