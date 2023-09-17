package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.esgi.pa.api.dtos.requests.materiel.CreateMaterielRequest;
import com.esgi.pa.api.dtos.requests.materiel.UpdateMaterielRequest;
import com.esgi.pa.api.dtos.responses.materiel.GetMaterielResponse;
import com.esgi.pa.api.mappers.MaterielMapper;
import com.esgi.pa.domain.entities.CategorieMateriel;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Materiel;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.NotAuthorizationRessourceException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.CategorieMaterielService;
import com.esgi.pa.domain.services.InternService;
import com.esgi.pa.domain.services.MaterielService;
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
@RequestMapping("/materiel")
@Api(tags = "Materiel API")
public class MaterielResource {

  private final UserService userService;
  private final InternService internService;
  private final MaterielService materielService;
  private final CategorieMaterielService categorieMaterielService;

  @GetMapping("{id}")
  public List<GetMaterielResponse> getAllMateriels(@PathVariable Long id)
    throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return MaterielMapper.toGetMaterielResponse(materielService.findAll());
    } else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @GetMapping("{id}/id/{idk}")
  public GetMaterielResponse getMaterielById(
    @PathVariable Long id,
    @PathVariable Long idk
  ) throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return MaterielMapper.toGetMaterielResponse(materielService.getById(idk));
    } else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @PostMapping(value = "{id}")
  @ResponseStatus(CREATED)
  public ResponseEntity<?> create(
    @Valid @RequestBody CreateMaterielRequest request,
    @PathVariable Long id
  ) throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    Intern intern = internService.getById(users);
    CategorieMateriel categorieMateriel = categorieMaterielService.getById(
      request.categorieMateriel()
    );
    materielService.create(
      intern,
      request.name(),
      request.description(),
      request.imgPath(),
      request.quantity(),
      request.price(),
      categorieMateriel,
      request.gallerie()
    );
    return ResponseEntity.status(CREATED).build();
  }

  @PutMapping(value = "{id}")
  @ResponseStatus(OK)
  public GetMaterielResponse update(
    @Valid @RequestBody UpdateMaterielRequest request,
    @PathVariable Long id
  )
    throws TechnicalFoundException, TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      Materiel materiel1 = materielService.getById(request.id());
      CategorieMateriel categorieMateriel = categorieMaterielService.getById(
        request.categorieMateriel()
      );
      Materiel materiel = materielService.update(
        materiel1,
        request.name(),
        request.description(),
        request.imgPath(),
        request.quantity(),
        request.price(),
        categorieMateriel,
        request.gallerie()
      );
      return MaterielMapper.toGetMaterielResponse(materiel);
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
      Materiel materiel = materielService.getById(idk);
      materielService.delete(materiel);
    } else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }
}
