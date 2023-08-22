package com.esgi.pa.api.dtos.requests.salle;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record GallerieRequest(
    @NotBlank(message = "fileName is required")
    String fileName
) {

}
