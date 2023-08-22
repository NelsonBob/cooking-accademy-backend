package com.esgi.pa.api.dtos.requests.user;

import com.esgi.pa.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

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
