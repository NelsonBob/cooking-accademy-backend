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

import com.esgi.pa.api.dtos.requests.cour.CreateCourRequest;
import com.esgi.pa.api.dtos.requests.cour.UpdateCourRequest;
import com.esgi.pa.api.dtos.responses.cour.GetCourResponse;
import com.esgi.pa.api.mappers.CourMapper;
import com.esgi.pa.domain.entities.Cour;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.NotAuthorizationRessourceException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.CourService;
import com.esgi.pa.domain.services.InternService;
import com.esgi.pa.domain.services.UserService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

/**
 * Contient les routes des Service Abonnement
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/cour")
@Api(tags = "Cour API")
public class CourResource {

  private final UserService userService;
  private final InternService internService;
  private final CourService courService;

  @GetMapping("{id}")
  public List<GetCourResponse> getAllCours(@PathVariable Long id)
    throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return CourMapper.toGetCourResponse(courService.findAll());
    }
    else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @GetMapping("{id}/id/{idk}")
  public GetCourResponse getCourById(
    @PathVariable Long id,
    @PathVariable Long idk
  ) throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return CourMapper.toGetCourResponse(courService.getById(idk));
    }
    else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @GetMapping("actif/{id}")
  public List<GetCourResponse> getCoursActif(@PathVariable Long id)
    throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      return CourMapper.toGetCourResponse(courService.findByStatus());
    }
    else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @PostMapping(value = "{id}")
  @ResponseStatus(CREATED)
  public GetCourResponse create(
    @Valid @RequestBody CreateCourRequest request,
    @PathVariable Long id
  ) throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    Intern intern = internService.getById(users);
    Cour cour = courService.create(
      intern,
      request.name(),
      request.description(),
      request.imgPath(),
      request.videoLink(),
      request.contentCour()
    );
    return CourMapper.toGetCourResponse(cour);
  }

  @PutMapping(value = "{id}")
  @ResponseStatus(OK)
  public GetCourResponse update(
    @Valid @RequestBody UpdateCourRequest request,
    @PathVariable Long id
  )
    throws TechnicalFoundException, TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      Cour cour1 = courService.getById(request.id());
      Cour cour = courService.update(
        cour1,
        request.name(),
        request.description(),
        request.imgPath(),
        request.videoLink(),
        request.contentCour(),
        request.status()
      );
      return CourMapper.toGetCourResponse(cour);
    }
    else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }

  @DeleteMapping(value = "{id}/id/{idk}")
  @ResponseStatus(OK)
  public void delete(@PathVariable Long idk, @PathVariable Long id)
    throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      Cour cour = courService.getById(idk);
      courService.delete(cour);
    }
    else throw new NotAuthorizationRessourceException(
      "Vous n'etes pas authorisé à accéder à cette ressource"
    );
  }
}
