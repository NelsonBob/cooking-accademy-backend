package com.esgi.pa.api.dtos.requests.cour;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de requête de création de compte
 *
 * @param id     id
 */
@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record GetByIdCourRequest(
    @NotBlank(message = "Long is required")
    Long id
) {

}
