package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.auth.AuthenticationUserResponse;
import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;
import com.esgi.pa.domain.entities.User;

import java.util.List;

/**
 * Contient les méthodes pour mapper les entités utilisateur du domain vers des dtos
 */
public interface UserMapper {

    static GetUserResponse toGetUserResponse(User user) {
        return new GetUserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole());
    }

    static List<GetUserResponse> toGetUserResponse(List<User> entities) {
        return entities.stream()
            .map(UserMapper::toGetUserResponse)
            .distinct()
            .toList();
    }

    static NoFriendsUserResponse toNoFriendsUserResponse(User user) {
        return new NoFriendsUserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole());
    }

    static List<NoFriendsUserResponse> toNoFriendsUserResponse(List<User> entities) {
        return entities.stream()
            .map(UserMapper::toNoFriendsUserResponse)
            .distinct()
            .toList();
    }

    static AuthenticationUserResponse toAuthenticationUserResponse(String token) {
        return new AuthenticationUserResponse(token);
    }
}
