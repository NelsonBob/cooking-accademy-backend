package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.esgi.pa.api.dtos.requests.repas.CreateRepasRequest;
import com.esgi.pa.api.dtos.requests.repas.UpdateRepasRequest;
import com.esgi.pa.api.dtos.responses.repas.GetRepasResponse;
import com.esgi.pa.api.mappers.RepasMapper;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Repas;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.NotAuthorizationRessourceException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.InternService;
import com.esgi.pa.domain.services.RepasService;
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
@RequestMapping("/repas")
@Api(tags = "Repas API")
public class RepasResource {

  private final UserService userService;
  private final InternService internService;
  private final RepasService repasService;

  @GetMapping("{id}")
  public List<GetRepasResponse> getAllRepass(@PathVariable Long id)
    throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return RepasMapper.toGetRepasResponse(repasService.findAll());
    } else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @GetMapping("{id}/id/{idk}")
  public GetRepasResponse getRepasById(
    @PathVariable Long id,
    @PathVariable Long idk
  ) throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return RepasMapper.toGetRepasResponse(repasService.getById(idk));
    } else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @PostMapping(value = "{id}")
  @ResponseStatus(CREATED)
  public ResponseEntity<?> create(
    @Valid @RequestBody CreateRepasRequest request,
    @PathVariable Long id
  ) throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    Intern intern = internService.getById(users);
    repasService.create(
      intern,
      request.name(),
      request.description(),
      request.imgPath(),
      request.quantity(),
      request.price()
    );
    return ResponseEntity.status(CREATED).build();
  }

  @PutMapping(value = "{id}")
  @ResponseStatus(OK)
  public GetRepasResponse update(
    @Valid @RequestBody UpdateRepasRequest request,
    @PathVariable Long id
  )
    throws TechnicalFoundException, TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      Repas repas1 = repasService.getById(request.id());
      Repas repas = repasService.update(
        repas1,
        request.name(),
        request.description(),
        request.imgPath(),
        request.quantity(),
        request.price()
      );
      return RepasMapper.toGetRepasResponse(repas);
    } else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @DeleteMapping(value = "{id}/id/{idk}")
  @ResponseStatus(OK)
  public void delete(@PathVariable Long id, @PathVariable Long idk)
    throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      Repas repas = repasService.getById(idk);
      repasService.delete(repas);
    } else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }
}
