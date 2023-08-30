package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.requests.intern.CreateInternRequest;
import com.esgi.pa.api.dtos.requests.intern.UpdateInternRequest;
import com.esgi.pa.api.dtos.requests.intern.UpdateProfileRequest;
import com.esgi.pa.api.dtos.responses.intern.GetInternResponse;
import com.esgi.pa.api.mappers.InternMapper;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.exceptions.NotAuthorizationRessourceException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.InternService;
import com.esgi.pa.domain.services.UserService;
import com.esgi.pa.domain.services.util.UtilService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

/**
 * Contient les routes des utilisateurs backoffice
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/intern")
@Api(tags = "Intern API")
public class InternResource {

  private final UserService userService;
  private final InternService internService;

  /**
   * Récupère un intern par son id
   *
   * @param id       id numérique de l'utilisateur
   * @param idintern id numérique de l'utilisateur
   * @return informations relative à l'utilisateur
   * @throws TechnicalNotFoundException         si l'utilisateur n'est pas trouvé
   * @throws NotAuthorizationRessourceException
   */
  @GetMapping("{id}/id/{idintern}")
  public GetInternResponse getInternById(
      @PathVariable Long id,
      @PathVariable Long idintern) throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      Users users1 = userService.getById(idintern);
      Intern intern1 = internService.getById(users1);
      return InternMapper.toGetInternResponse(intern1);
    } else
      throw new NotAuthorizationRessourceException(
          "Vous n'etes pas authorisé à accéder à cette ressource");
  }

  @PostMapping(value = "create/{id}")
  @ResponseStatus(CREATED)
  public GetInternResponse createIntern(
      @Valid @RequestBody CreateInternRequest request,
      @PathVariable Long id)
      throws TechnicalFoundException, TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      Intern intern = internService.create(
          request.name(),
          request.email(),
          request.password(),
          request.role(),
          request.fonction());
      return InternMapper.toGetInternResponse(intern);
    } else
      throw new NotAuthorizationRessourceException(
          "Vous n'etes pas authorisé à accéder à cette ressource");
  }

  @PutMapping(value = "{id}")
  @ResponseStatus(OK)
  public GetInternResponse updateProfile(
      @Valid @RequestBody UpdateProfileRequest request,
      @PathVariable Long id) throws TechnicalNotFoundException {
    Users users = userService.getById(id);
    Intern intern = internService.getById(users);
    Intern intern1 = internService.updateProfile(
        users,
        intern,
        request.name(),
        request.fonction());
    return InternMapper.toGetInternResponse(intern1);
  }

  @PutMapping(value = "update/{id}")
  @ResponseStatus(OK)
  public GetInternResponse updateIntern(
      @Valid @RequestBody UpdateInternRequest request,
      @PathVariable Long id)
      throws TechnicalNotFoundException, TechnicalFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      Intern intern2 = internService.getByIdIntern(request.id());

      Intern intern1 = internService.update(
          intern2,
          request.name(),
          request.email(),
          request.role(),
          request.fonction());
      return InternMapper.toGetInternResponse(intern1);
    } else
      throw new NotAuthorizationRessourceException(
          "Vous n'etes pas authorisé à accéder à cette ressource");
  }

  @GetMapping("list/{id}")
  public List<GetInternResponse> getInternAll(@PathVariable Long id)
      throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (internService.doesExistForUsers(users)) {
      if (UtilService.isGranted(users.getRole(), Arrays.asList(RoleEnum.Admin))) {
        return InternMapper.toGetInternResponse(internService.findAll());
      } else
        throw new NotAuthorizationRessourceException(
            "Vous n'etes pas authorisé à accéder à cette ressource");
    } else
      throw new NotAuthorizationRessourceException(
          "Vous n'etes pas authorisé à accéder à cette ressource");
  }
}
