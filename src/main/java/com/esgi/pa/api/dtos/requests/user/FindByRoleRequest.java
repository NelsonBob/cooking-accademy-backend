package com.esgi.pa.api.dtos.requests.user;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;

import com.esgi.pa.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

/**
 * DTO de requête de création de compte
 *
 * @param role    role de l'utilisateur
 */
@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record FindByRoleRequest(
    @NotBlank(message = "role is required")
    RoleEnum role
) {

}
