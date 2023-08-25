package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.OK;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.requests.intern.UpdateProfileRequest;
import com.esgi.pa.api.dtos.responses.client.GetClientResponse;
import com.esgi.pa.api.mappers.ClientMapper;
import com.esgi.pa.domain.entities.Client;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.exceptions.NotAuthorizationRessourceException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.ClientService;
import com.esgi.pa.domain.services.UserService;
import com.esgi.pa.domain.services.util.UtilService;

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
  @GetMapping("{id}/id/{idclient}")
  public GetClientResponse getClientById(
    @PathVariable Long id,
    @PathVariable Long idintern
  ) throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (clientService.doesExistForUsers(users)) {
      return ClientMapper.toGetClientResponse(
        clientService.getByIdClient(idintern)
      );
    }
    throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @GetMapping("list/{id}")
  public List<GetClientResponse> getClientAll(@PathVariable Long id)
    throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (clientService.doesExistForUsers(users)) {
      if (
        UtilService.isGranted(users.getRole(), Arrays.asList(RoleEnum.Admin))
      ) {
        List<Client> clients = clientService.findAll();

        return ClientMapper.toGetClientResponse(clients);
      }
      throw new NotAuthorizationRessourceException(
        "Vous n'etes pas authorisé à accéder à cette ressource"
      );
    }
    throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @PutMapping(value = "update/{id}")
  @ResponseStatus(OK)
  public GetClientResponse updateProfile(
    @Valid @RequestBody UpdateProfileRequest request,
    @PathVariable Long id
  ) throws TechnicalNotFoundException {
    Users users = userService.getById(id);
    Client client = clientService.getById(users);
    Client client1 = clientService.updateProfile(
      users,
      client,
      request.name(),
      request.fonction()
    );
    return ClientMapper.toGetClientResponse(client1);
  }
}
