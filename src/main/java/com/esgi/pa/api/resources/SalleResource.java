package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import javax.validation.Valid;

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

import com.esgi.pa.api.dtos.requests.salle.CreateSalleRequest;
import com.esgi.pa.api.dtos.requests.salle.UpdateSalleRequest;
import com.esgi.pa.api.dtos.responses.salle.GetSalleResponse;
import com.esgi.pa.api.mappers.SalleMapper;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Salle;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.NotAuthorizationRessourceException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.InternService;
import com.esgi.pa.domain.services.SalleService;
import com.esgi.pa.domain.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

/**
 * Contient les routes des Service Abonnement
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/salle")
@Api(tags = "Salle API")
public class SalleResource {

  private final UserService userService;
  private final InternService internService;
  private final SalleService salleService;

  @GetMapping("{id}")
  public List<GetSalleResponse> getAllSalles(@PathVariable Long id)
      throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return SalleMapper.toGetSalleResponse(salleService.findAll());
    } else
      throw new NotAuthorizationRessourceException(
          "Vous n'etes pas authorisé à accéder à cette ressource");
  }

  @GetMapping("{id}/id/{idk}")
  public GetSalleResponse getSalleById(
      @PathVariable Long id,
      @PathVariable Long idk) throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return SalleMapper.toGetSalleResponse(salleService.getById(idk));
    } else
      throw new NotAuthorizationRessourceException(
          "Vous n'etes pas authorisé à accéder à cette ressource");
  }

  @GetMapping("actif/{id}")
  public List<GetSalleResponse> getSallesActif(@PathVariable Long id)
      throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return SalleMapper.toGetSalleResponse(salleService.findByStatus());
    } else
      throw new NotAuthorizationRessourceException(
          "Vous n'etes pas authorisé à accéder à cette ressource");
  }

  @PostMapping(value = "{id}")
  @ResponseStatus(CREATED)
  public ResponseEntity<?> create(
      @Valid @RequestBody CreateSalleRequest request,
      @PathVariable Long id)
      throws TechnicalFoundException, TechnicalNotFoundException, JsonProcessingException {
    Users users = userService.getById(id);
    Intern intern = internService.getById(users);

    salleService.create(
        intern,
        request.name(),
        request.description(),
        request.imgPath(),
        request.gallerie());
    return ResponseEntity.status(CREATED).build();
  }

  @PutMapping(value = "{id}")
  @ResponseStatus(OK)
  public GetSalleResponse update(
      @Valid @RequestBody UpdateSalleRequest request,
      @PathVariable Long id)
      throws TechnicalFoundException, TechnicalNotFoundException, JsonProcessingException,
      NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      Salle salle1 = salleService.getById(request.id());
      Salle salle = salleService.update(
          salle1,
          request.name(),
          request.description(),
          request.imgPath(),
          request.gallerie(),
          request.status());
      return SalleMapper.toGetSalleResponse(salle);
    } else
      throw new NotAuthorizationRessourceException(
          "Vous n'etes pas authorisé à accéder à cette ressource");
  }

  @DeleteMapping(value = "{id}/id/{idk}")
  @ResponseStatus(OK)
  public void delete(@PathVariable Long id, @PathVariable Long idk)
      throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      Salle salle = salleService.getById(idk);
      salleService.delete(salle);
    } else
      throw new NotAuthorizationRessourceException(
          "Vous n'etes pas authorisé à accéder à cette ressource");
  }
}
