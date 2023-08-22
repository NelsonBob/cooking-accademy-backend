package com.esgi.pa.api.dtos.responses.client;

import com.esgi.pa.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetClientResponse(
    Long id,
    String name,
    String email,
    RoleEnum role,
    String adress) {
}
