package com.esgi.pa.api.dtos.responses.intern;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetInternResponse(
        Long id,
        String name,
        String email,
        RoleEnum role,
        String fonction,
        String imgPath) {
}
