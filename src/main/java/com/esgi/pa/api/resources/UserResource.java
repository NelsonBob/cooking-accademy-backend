package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.user.FindByRoleRequest;
import com.esgi.pa.domain.entities.Users;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.requests.user.FindByEmailRequest;
import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.UserService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
    Users users =userService.getByEmail(request.email());
    return UserMapper.toGetUserResponse(users);
  }
  @GetMapping()
  public List<GetUserResponse> getUserByRole(FindByRoleRequest request){
    List<Users> users =userService.getByRole(request.role());
    return UserMapper.toGetUserResponse(users);
  }
}
