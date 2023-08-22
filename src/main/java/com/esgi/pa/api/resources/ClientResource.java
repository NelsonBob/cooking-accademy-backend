package com.esgi.pa.api.resources;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.responses.client.GetClientResponse;
import com.esgi.pa.api.mappers.ClientMapper;
import com.esgi.pa.domain.entities.Client;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.ClientService;
import com.esgi.pa.domain.services.UserService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

/**
 * Contient les routes des clients
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Api(tags = "Client API")
public class ClientResource {

  private final UserService userService;
  private final ClientService clientService;

  /**
   * Récupère un utilisateur par son id
   *
   * @param id id numérique de l'utilisateur
   * @return informations relative à l'utilisateur
   * @throws TechnicalNotFoundException si l'utilisateur n'est pas trouvé
   */
  @GetMapping("{id}")
  public GetClientResponse getClientById(@PathVariable Long id)
    throws TechnicalNotFoundException {
    Users users = userService.getById(id);
    Client client = clientService.getById(users);
    return ClientMapper.toGetClientResponse(users, client);
  }
}
