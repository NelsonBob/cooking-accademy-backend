package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.esgi.pa.api.dtos.requests.optionAbonnement.CreateOptionAbonnementRequest;
import com.esgi.pa.api.dtos.requests.optionAbonnement.UpdateOptionAbonnementRequest;
import com.esgi.pa.api.dtos.responses.optionAbonnement.GetOptionAbonnementResponse;
import com.esgi.pa.api.mappers.OptionAbonnementMapper;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.OptionAbonnement;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.NotAuthorizationRessourceException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.InternService;
import com.esgi.pa.domain.services.OptionAbonnementService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/option-abonnement")
@Api(tags = "OptionAbonnement API")
public class OptionAbonnementResource {

  private final UserService userService;
  private final InternService internService;
  private final OptionAbonnementService optionAbonnementService;

  @GetMapping("{id}")
  public List<GetOptionAbonnementResponse> getAllOptionAbonnements(
      @PathVariable Long id) throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return OptionAbonnementMapper.toGetOptionAbonnementResponse(
          optionAbonnementService.findAll());
    } else
      throw new NotAuthorizationRessourceException(
          "Vous n'etes pas authorisé à accéder à cette ressource");
  }

  @GetMapping("{id}/id/{idk}")
  public GetOptionAbonnementResponse getOptionAbonnementById(
      @PathVariable Long id,
      @PathVariable Long idk) throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return OptionAbonnementMapper.toGetOptionAbonnementResponse(
          optionAbonnementService.getById(idk));
    } else
      throw new NotAuthorizationRessourceException(
          "Vous n'etes pas authorisé à accéder à cette ressource");
  }

  @PostMapping(value = "{id}")
  @ResponseStatus(CREATED)
  public ResponseEntity<?> create(
      @Valid @RequestBody CreateOptionAbonnementRequest request,
      @PathVariable Long id) throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    Intern intern = internService.getById(users);
    optionAbonnementService.create(
        intern,
        request.name(),
        request.optionServiceAbonnementRequests());
    return ResponseEntity.status(CREATED).build();
  }

  @PutMapping(value = "{id}")
  @ResponseStatus(OK)
  public GetOptionAbonnementResponse update(
      @Valid @RequestBody UpdateOptionAbonnementRequest request,
      @PathVariable Long id)
      throws TechnicalFoundException, TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      OptionAbonnement optionAbonnement1 = optionAbonnementService.getById(
          request.id());
      OptionAbonnement optionAbonnement = optionAbonnementService.update(
          optionAbonnement1,
          request.name(),
          request.status(),
          request.optionServiceAbonnementRequests());
      return OptionAbonnementMapper.toGetOptionAbonnementResponse(
          optionAbonnement);
    } else
      throw new NotAuthorizationRessourceException(
          "Vous n'etes pas authorisé à accéder à cette ressource");
  }

  @DeleteMapping(value = "{id}/id/{idk}")
  @ResponseStatus(OK)
  public void delete(@PathVariable Long idk, @PathVariable Long id)
      throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      OptionAbonnement optionAbonnement = optionAbonnementService.getById(idk);
      optionAbonnementService.delete(optionAbonnement);
    } else
      throw new NotAuthorizationRessourceException(
          "Vous n'etes pas authorisé à accéder à cette ressource");
  }
}
