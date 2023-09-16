package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.OK;

import com.esgi.pa.api.dtos.requests.user.FindByEmailRequest;
import com.esgi.pa.api.dtos.requests.user.FindByRoleRequest;
import com.esgi.pa.api.dtos.requests.user.UpdatePictureRequest;
import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.esgi.pa.api.dtos.responses.user.GetUserSimpleResponse;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.NotAuthorizationRessourceException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contient les routes des utilisateurs
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "User API")
public class UserResource {

  private final UserService userService;

  @GetMapping("email")
  public GetUserResponse getUserByEmail(FindByEmailRequest request)
    throws TechnicalNotFoundException {
    Users users = userService.getByEmail(request.email());
    return UserMapper.toGetUserResponse(users);
  }

  @GetMapping
  public List<GetUserResponse> getUserByRole(FindByRoleRequest request) {
    List<Users> users = userService.getByRole(request.role());
    return UserMapper.toGetUserResponse(users);
  }

  @GetMapping("{id}")
  public List<GetUserSimpleResponse> getListUsers(@PathVariable Long id)
    throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    List<Users> usersList = userService.listUsers(users.getId());
    return UserMapper.toGetUserSimpleResponse(usersList);
  }

  @PutMapping(value = "{id}")
  @ResponseStatus(OK)
  public ResponseEntity<?> update(
    @Valid @RequestBody UpdatePictureRequest request,
    @PathVariable Long id
  )
    throws TechnicalFoundException, TechnicalNotFoundException, JsonProcessingException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    userService.updatePicture(users, request.imgPath());
    return ResponseEntity.status(OK).build();
  }
}
