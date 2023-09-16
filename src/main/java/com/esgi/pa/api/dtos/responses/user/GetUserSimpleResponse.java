package com.esgi.pa.api.dtos.responses.user;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetUserSimpleResponse(
    Long id,
    String name) {
}
