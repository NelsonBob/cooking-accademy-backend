package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.esgi.pa.api.dtos.requests.categorieMateriel.CreateCategorieMaterielRequest;
import com.esgi.pa.api.dtos.requests.categorieMateriel.UpdateCategorieMaterielRequest;
import com.esgi.pa.api.dtos.responses.categorieMateriel.GetCategorieMaterielResponse;
import com.esgi.pa.api.mappers.CategorieMaterielMapper;
import com.esgi.pa.domain.entities.CategorieMateriel;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.NotAuthorizationRessourceException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.CategorieMaterielService;
import com.esgi.pa.domain.services.InternService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
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
@RequestMapping("/categorie-materiel")
@Api(tags = "Categorie Materiel API")
public class CategorieMaterielResource {

  private final UserService userService;
  private final InternService internService;
  private final CategorieMaterielService categorieMaterielService;

  @GetMapping("{id}")
  public List<GetCategorieMaterielResponse> getAllCategorieMateriels(
    @PathVariable Long id
  ) throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return CategorieMaterielMapper.toGetCategorieMaterielResponse(
        categorieMaterielService.findAll()
      );
    }
    else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @GetMapping("{id}/id/{idk}")
  public GetCategorieMaterielResponse getCategorieMaterielById(
    @PathVariable Long id,
    @PathVariable Long idk
  ) throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return CategorieMaterielMapper.toGetCategorieMaterielResponse(
        categorieMaterielService.getById(idk)
      );
    }
    else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @PostMapping(value = "{id}")
  @ResponseStatus(CREATED)
  public GetCategorieMaterielResponse create(
    @Valid @RequestBody CreateCategorieMaterielRequest request,
    @PathVariable Long id
  ) throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    Intern intern = internService.getById(users);
    CategorieMateriel categorieMateriel = categorieMaterielService.create(
      intern,
      request.name()
    );
    return CategorieMaterielMapper.toGetCategorieMaterielResponse(
      categorieMateriel
    );
  }

  @PutMapping(value = "{id}")
  @ResponseStatus(OK)
  public GetCategorieMaterielResponse update(
    @Valid @RequestBody UpdateCategorieMaterielRequest request,
    @PathVariable Long id
  )
    throws TechnicalFoundException, TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      CategorieMateriel categorieMateriel1 = categorieMaterielService.getById(
        request.id()
      );
      CategorieMateriel categorieMateriel = categorieMaterielService.update(
        categorieMateriel1,
        request.name()
      );
      return CategorieMaterielMapper.toGetCategorieMaterielResponse(
        categorieMateriel
      );
    }
    else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @DeleteMapping(value = "{id}/id/{idk}")
  @ResponseStatus(OK)
  public void delete(@PathVariable Long idk, @PathVariable Long id)
    throws TechnicalNotFoundException {
    Users users = userService.getById(id);
    internService.getById(users);
    CategorieMateriel categorieMateriel = categorieMaterielService.getById(idk);
    categorieMaterielService.delete(categorieMateriel);
  }
}
