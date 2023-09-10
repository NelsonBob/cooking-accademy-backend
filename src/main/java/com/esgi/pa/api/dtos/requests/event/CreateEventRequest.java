package com.esgi.pa.api.dtos.requests.event;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record CreateEventRequest(
        @NotBlank(message = "title is required") String title,
        @NotBlank(message = "start is required") String start,
        @NotBlank(message = "end is required") String end,
        @NotBlank(message = "imgPath is required") String imgPath,
        @NotNull(message = "elementId is required") long elementId) {
}
