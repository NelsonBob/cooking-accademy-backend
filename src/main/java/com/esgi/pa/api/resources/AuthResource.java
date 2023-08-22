package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;

import com.esgi.pa.domain.enums.RoleEnum;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.requests.auth.CreateUserRequest;
import com.esgi.pa.api.dtos.requests.auth.UserLoginRequest;
import com.esgi.pa.api.dtos.responses.auth.AuthenticationUserResponse;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.AuthService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

/**
 * Contient les routes d'authentification
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Api(tags = "Authentication API")
public class AuthResource {

  private final AuthService authService;

  /**
   * Traite les requêtes de connexion
   *
   * @param request contient les identifiants de connexion
   * @return JWT permettant l'authentification sur les autres requêtes de l'application
   * @throws TechnicalNotFoundException si les identifiants ne permettent pas de trouver un utilisateur
   */
  @PostMapping(value = "login")
  @ResponseStatus(OK)
  public AuthenticationUserResponse login(
    @RequestBody @Valid UserLoginRequest request
  ) throws TechnicalNotFoundException {
    return UserMapper.toAuthenticationUserResponse(
      authService.login(request.email(), request.password())
    );
  }

  /**
   * Traite les requêtes de création de compte
   *
   * @param request contient les informations permettant la création d'un compte
   * @return JWT permettant l'authentification sur les autres requêtes de l'application
   * @throws TechnicalFoundException si les informations fourni existe déjà en base
   */
  @PostMapping(value = "signup")
  @ResponseStatus(CREATED)
  public AuthenticationUserResponse signup(
    @Valid @RequestBody CreateUserRequest request
  ) throws TechnicalFoundException {
    return UserMapper.toAuthenticationUserResponse(
      authService.create(
        request.name(),
        request.email(),
        request.password(),
        RoleEnum.Client,
        request.adress()
      )
    );
  }
}
