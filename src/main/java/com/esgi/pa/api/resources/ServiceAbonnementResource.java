package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.esgi.pa.api.dtos.requests.serviceAbonnement.CreateServiceAbonnementRequest;
import com.esgi.pa.api.dtos.requests.serviceAbonnement.UpdateServiceAbonnementRequest;
import com.esgi.pa.api.dtos.responses.serviceAbonnement.GetServiceAbonnementResponse;
import com.esgi.pa.api.mappers.ServiceAbonnementMapper;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.ServiceAbonnement;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.NotAuthorizationRessourceException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.InternService;
import com.esgi.pa.domain.services.ServiceAbonnementService;
import com.esgi.pa.domain.services.UserService;
import com.esgi.pa.domain.services.util.UtilService;

import io.swagger.annotations.Api;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contient les routes des Service Abonnement
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/service-abonnement")
@Api(tags = "ServiceAbonnement API")
public class ServiceAbonnementResource {

  private final UserService userService;
  private final InternService internService;
  private final ServiceAbonnementService serviceAbonnementService;

  @GetMapping("{id}")
  public List<GetServiceAbonnementResponse> getAllServiceAbonnements(
    @PathVariable Long id
  ) throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return ServiceAbonnementMapper.toGetServiceAbonnementResponse(
        serviceAbonnementService.findAll()
      );
    }
    else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @GetMapping("{id}/id/{idk}")
  public GetServiceAbonnementResponse getServiceAbonnementById(
    @PathVariable Long id,
    @PathVariable Long idk
  ) throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return ServiceAbonnementMapper.toGetServiceAbonnementResponse(
        serviceAbonnementService.getById(idk)
      );
    }
    else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @GetMapping("actif/{id}")
  public List<GetServiceAbonnementResponse> getServiceAbonnementsActif(
    @PathVariable Long id
  ) throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return ServiceAbonnementMapper.toGetServiceAbonnementResponse(
        serviceAbonnementService.findByStatus()
      );
    }
    else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @PostMapping(value = "{id}")
  @ResponseStatus(CREATED)
  public GetServiceAbonnementResponse create(
    @Valid @RequestBody CreateServiceAbonnementRequest request,
    @PathVariable Long id
  ) throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    Intern intern = internService.getById(users);
    ServiceAbonnement serviceAbonnement = serviceAbonnementService.create(
      intern,
      request.name(),
      request.description(),
      request.imgPath()
    );
    return ServiceAbonnementMapper.toGetServiceAbonnementResponse(
      serviceAbonnement
    );
  }

  @PutMapping(value = "{id}")
  @ResponseStatus(OK)
  public GetServiceAbonnementResponse update(
    @Valid @RequestBody UpdateServiceAbonnementRequest request,
    @PathVariable Long id
  )
    throws TechnicalFoundException, TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      ServiceAbonnement serviceAbonnement1 = serviceAbonnementService.getById(
        request.id()
      );
      ServiceAbonnement serviceAbonnement = serviceAbonnementService.update(
        serviceAbonnement1,
        request.name(),
        request.description(),
        request.status(),
        request.imgPath()
      );
      return ServiceAbonnementMapper.toGetServiceAbonnementResponse(
        serviceAbonnement
      );
    }
    else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @DeleteMapping(value = "{id}/id/{idk}")
  @ResponseStatus(OK)
  public void delete(@PathVariable Long id, @PathVariable Long idk)
    throws TechnicalNotFoundException, NotAuthorizationRessourceException, IOException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      ServiceAbonnement serviceAbonnement = serviceAbonnementService.getById(
        idk
      );
      UtilService.removeFile(serviceAbonnement.getImgPath());
      serviceAbonnementService.delete(serviceAbonnement);
    } else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }
}
