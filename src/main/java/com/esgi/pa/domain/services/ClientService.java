package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Client;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.ClientRepository;
import com.esgi.pa.server.repositories.UsersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des utilisateurs
 */
@Service
@RequiredArgsConstructor
public class ClientService {

  private final ClientRepository clientRepository;
  private final UsersRepository usersRepository;

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

  public boolean doesExistForUsers(Users users) {
    return clientRepository.existsByUsers(users);
  }

  public Client getByIdClient(Long id) throws TechnicalNotFoundException {
    return clientRepository
      .findById(id)
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

  public List<Client> findAll() {
    return clientRepository.findAll();
  }

  public Client updateProfile(
    Users users,
    Client client,
    String name,
    String adress
  ) {
    users.setName(name);
    usersRepository.save(users);
    client.setAdress(adress);
    return clientRepository.save(client);
  }
}
