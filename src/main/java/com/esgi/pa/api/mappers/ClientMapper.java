package com.esgi.pa.api.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.esgi.pa.api.dtos.responses.client.GetClientResponse;
import com.esgi.pa.domain.entities.Client;
import com.esgi.pa.domain.entities.Users;

/**
 * Contient les méthodes pour mapper les entités utilisateur du domain vers des dtos
 */
public interface ClientMapper {

    static GetClientResponse toGetClientResponse(Users users, Client client) {
        return new GetClientResponse(
                users.getId(),
                users.getName(),
                users.getEmail(),
                users.getRole(),
                client.getAdress());
    }
    public static List<GetClientResponse> toGetClientResponse(List<Users> entities, List<Client> clients) {
        return entities.stream()
                .map(entity -> toGetClientResponse(entity, getClientByUser(entity, clients)))
                .distinct()
                .collect(Collectors.toList());
    }

    private static Client getClientByUser(Users users, List<Client> clients) {
        return clients.stream()
                .filter(client -> client.getUsers().equals(users))
                .findFirst()
                .orElse(null);
    }

}
