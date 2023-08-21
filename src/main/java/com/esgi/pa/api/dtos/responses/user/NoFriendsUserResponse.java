package com.esgi.pa.api.dtos.responses.user;

import com.esgi.pa.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de réponse à une requête d'informations utilisateur - amis omis -
 *
 * @param id    id numérique de l'utilisateur
 * @param name  nom de l'utilisateur
 * @param email email de l'utilisateur
 * @param role  rôle de l'utilisateur
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record NoFriendsUserResponse(Long id,
                                    String name,
                                    String email,
                                    RoleEnum role) {
}
