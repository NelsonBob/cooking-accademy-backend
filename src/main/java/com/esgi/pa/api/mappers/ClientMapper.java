package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.client.GetClientResponse;
import com.esgi.pa.domain.entities.Client;
import java.util.List;

/**
 * Contient les méthodes pour mapper les entités utilisateur du domain vers des dtos
 */
public interface ClientMapper {
  static GetClientResponse toGetClientResponse(Client client) {
    return new GetClientResponse(
      client.getUsers().getId(),
      client.getUsers().getName(),
      client.getUsers().getEmail(),
      client.getUsers().getRole(),
      client.getAdress()
    );
  }

  static List<GetClientResponse> toGetClientResponse(List<Client> entities) {
    return entities
      .stream()
      .map(ClientMapper::toGetClientResponse)
      .distinct()
      .toList();
  }
}
