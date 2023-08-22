package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.auth.AuthenticationUserResponse;
import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.esgi.pa.domain.entities.Users;

/**
 * Contient les méthodes pour mapper les entités utilisateur du domain vers des dtos
 */
public interface UserMapper {

    static GetUserResponse toGetUserResponse(Users users) {
        return new GetUserResponse(
                users.getId(),
                users.getName(),
                users.getEmail(),
                users.getRole());
    }
    static List<GetUserResponse> toGetUserResponse(List<Users> entities) {
        return entities.stream()
            .map(UserMapper::toGetUserResponse)
            .distinct()
            .toList();
    }

    static AuthenticationUserResponse toAuthenticationUserResponse(String token) {
        return new AuthenticationUserResponse(token);
    }
}
