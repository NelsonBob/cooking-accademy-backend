package com.esgi.pa.api.dtos.requests.intern;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.esgi.pa.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

/**
 * DTO de requête de création de compte
 *
 * @param name     nom de l'utilisateur
 * @param email    email de l'utilisateur
 * @param role     role de l'utilisateur
 * @param fonction     role de l'utilisateur
 */
@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UpdateInternRequest(
  @NotBlank(message = "Name is required") String name,
  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  String email,
  @NotNull(message = "Role is required") RoleEnum role,
  @NotBlank(message = "Fonction is required") String fonction,
  @NotNull(message = "Long is required") Long id
) {}
