package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Client;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.security.JwtService;
import com.esgi.pa.domain.services.util.UtilService;
import com.esgi.pa.server.repositories.ClientRepository;
import com.esgi.pa.server.repositories.InternRepository;
import com.esgi.pa.server.repositories.UsersRepository;
import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Serivce d'authentification de l'application
 */
@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtService jwtService;
  private final UsersRepository usersRepository;
  private final ClientRepository clientRepository;
  private final InternRepository internRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  /**
   * Permet la création d'un nouvel utilisateur
   *
   * @param name     nom de l'utilisateur
   * @param email    email de l'utilisateur
   * @param password mot de passe de l'utilisateur
   * @param role     rôle de l'utilisateur
   * @return un token JWT pour l'utilisateur
   * @throws TechnicalFoundException si l'utilisateur existe déjà
   */
  public String create(
    String name,
    String email,
    String password,
    RoleEnum role,
    String adress
  ) throws TechnicalFoundException {
    if (usersRepository.findByEmail(email).isEmpty()) {
      Users savedUser = usersRepository.save(
        Users
          .builder()
          .name(name)
          .email(email)
          .password(passwordEncoder.encode(password))
          .role(role)
          .build()
      );
      Users users = usersRepository
        .findByEmail(savedUser.getEmail())
        .orElseThrow();
      if (role == RoleEnum.Client) clientRepository.save(
        Client.builder().users(users).adress(adress).nbVideoDay(0).build()
      );
      if (role == RoleEnum.Admin) internRepository.save(
        Intern.builder().fonction(adress).users(users).build()
      );
      return jwtService.generateToken(
        Map.of(
          "id",
          users.getId(),
          "name",
          users.getName(),
          "role",
          users.getRole(),
          "adress",
          adress
        ),
        users
      );
    } else {
      throw new TechnicalFoundException(
        "Un compte existe Déjà avec cet email :" + email
      );
    }
  }

  /**
   * Permet la connexion d'un utilisateur existant
   *
   * @param email    email de l'utilisateur
   * @param password mot dep passe de l'utilisateur
   * @return un token JWT pour l'utilisateur
   * @throws TechnicalNotFoundException si l'utilisateur n'existe pas
   */
  public String login(String email, String password)
    throws TechnicalNotFoundException {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(email, password)
    );
    Users users = usersRepository
      .findByEmail(email)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "Username not found with email : " + email
        )
      );

    if (
      UtilService.isGranted(users.getRole(), Arrays.asList(RoleEnum.Client))
    ) {
      Client client = clientRepository
        .findByUsers(users)
        .orElseThrow(() ->
          new TechnicalNotFoundException(
            HttpStatus.NOT_FOUND,
            "Client not found with email : " + email
          )
        );
      return jwtService.generateToken(
        Map.of(
          "id",
          users.getId(),
          "name",
          users.getName(),
          "role",
          users.getRole(),
          "picture",
          users.getImgPath() != null ? users.getImgPath() : "",
          "adress",
          client.getAdress()
        ),
        users
      );
    } else {
      Intern intern = internRepository
        .findByUsers(users)
        .orElseThrow(() ->
          new TechnicalNotFoundException(
            HttpStatus.NOT_FOUND,
            "Intern not found with email : " + email
          )
        );
      return jwtService.generateToken(
        Map.of(
          "id",
          users.getId(),
          "name",
          users.getName(),
          "role",
          users.getRole(),
          "picture",
          users.getImgPath() != null ? users.getImgPath() : "",
          "fonction",
          intern.getFonction()
        ),
        users
      );
    }
  }
}
