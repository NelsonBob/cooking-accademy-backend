package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import javax.validation.Valid;

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
import com.esgi.pa.api.dtos.requests.salle.GetByIdSalleRequest;
import com.esgi.pa.api.dtos.requests.salle.UpdateSalleRequest;
import com.esgi.pa.api.dtos.responses.salle.GetSalleResponse;
import com.esgi.pa.api.mappers.SalleMapper;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Salle;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.InternService;
import com.esgi.pa.domain.services.SalleService;
import com.esgi.pa.domain.services.UserService;

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
    throws TechnicalNotFoundException {
    Users users = userService.getById(id);
    Intern intern = internService.getById(users);
    return SalleMapper.toGetSalleResponse(salleService.findAll());
  }

  @GetMapping("user/{id}")
  public GetSalleResponse getSalleById(
    @PathVariable Long id,
    @Valid @RequestBody GetByIdSalleRequest request
  ) throws TechnicalNotFoundException {
    Users users = userService.getById(id);
    Intern intern = internService.getById(users);
    return SalleMapper.toGetSalleResponse(salleService.getById(request.id()));
  }

  @GetMapping("actif/{id}")
  public List<GetSalleResponse> getSallesActif(@PathVariable Long id)
    throws TechnicalNotFoundException {
    Users users = userService.getById(id);
    Intern intern = internService.getById(users);
    return SalleMapper.toGetSalleResponse(salleService.findByStatus());
  }

  @PostMapping(value = "{id}")
  @ResponseStatus(CREATED)
  public GetSalleResponse create(
    @Valid @RequestBody CreateSalleRequest request,
    @PathVariable Long id
  ) throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    Intern intern = internService.getById(users);
    Salle salle = salleService.create(
      intern,
      request.name(),
      request.description(),
      request.imgPath(),
      request.gallerie()
    );
    return SalleMapper.toGetSalleResponse(salle);
  }

  @PutMapping(value = "{id}")
  @ResponseStatus(OK)
  public GetSalleResponse update(
    @Valid @RequestBody UpdateSalleRequest request,
    @PathVariable Long id
  ) throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    Intern intern = internService.getById(users);
    Salle salle1 = salleService.getById(request.id());
    Salle salle = salleService.update(
      salle1,
      request.name(),
      request.description(),
      request.imgPath(),
      request.gallerie(),
      request.status()
    );
    return SalleMapper.toGetSalleResponse(salle);
  }

  @DeleteMapping(value = "{id}")
  @ResponseStatus(OK)
  public void delete(
    @Valid @RequestBody GetByIdSalleRequest request,
    @PathVariable Long id
  ) throws TechnicalNotFoundException {
    Users users = userService.getById(id);
    Intern intern = internService.getById(users);
    Salle salle = salleService.getById(request.id());
    salleService.delete(salle);
  }
}
