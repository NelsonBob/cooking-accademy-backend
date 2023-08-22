package com.esgi.pa.domain.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Client;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.ClientRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service de gestion des utilisateurs
 */
@Service
@RequiredArgsConstructor
public class ClientService {

  private final ClientRepository clientRepository;

  /**
   * Récupère un utilisateur par son id
   *
   * @param user  numérique de l'utilsateur
   * @return utilisateur recherché
   * @throws TechnicalNotFoundException si l'utilisateur n'est pas trouvé
   */
  public Client getById(Users user) throws TechnicalNotFoundException {
    return clientRepository
      .findByUsers(user)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No client found with following id : "
        )
      );
  }

  /**
   * Persiste un utilisateur
   *
   * @param client utilisateur à sauvegarder
   * @return l'utilisateur persité
   */
  public Client save(Client client) {
    return clientRepository.save(client);
  }
}
