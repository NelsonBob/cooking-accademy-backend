package com.esgi.pa.api.dtos.requests.event;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotNull;

import com.esgi.pa.domain.enums.TypeEventEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record GetTypeEventRequest(
        @NotNull(message = "type is required") TypeEventEnum type) {
}
