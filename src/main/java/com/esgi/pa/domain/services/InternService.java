package com.esgi.pa.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.InternRepository;
import com.esgi.pa.server.repositories.UsersRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service de gestion des utilisateurs
 */
@Service
@RequiredArgsConstructor
public class InternService {

  private final InternRepository internRepository;
  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * Récupère un utilisateur par son id
   *
   * @param user numérique de l'utilsateur
   * @return utilisateur recherché
   * @throws TechnicalNotFoundException si l'utilisateur n'est pas trouvé
   */
  public Intern getById(Users user) throws TechnicalNotFoundException {
    return internRepository
        .findByUsers(user)
        .orElseThrow(() -> new TechnicalNotFoundException(
            HttpStatus.NOT_FOUND,
            "No inter found with following id : "));
  }

  public List<Intern> findTop4Chefs() {
    return internRepository.findTop4ByUsersRole(RoleEnum.Chefs);
  }

  public Intern getByIdIntern(Long id) throws TechnicalNotFoundException {
    return internRepository
        .findById(id)
        .orElseThrow(() -> new TechnicalNotFoundException(
            HttpStatus.NOT_FOUND,
            "No inter found with following id : "));
  }

  public boolean doesExistForUsers(Users users) {
    return internRepository.existsByUsers(users);
  }

  public Intern create(
      String name,
      String email,
      String password,
      RoleEnum role,
      String fonction) throws TechnicalFoundException {
    if (usersRepository.findByEmail(email).isEmpty()) {
      Users savedUser = usersRepository.save(
          Users
              .builder()
              .name(name)
              .email(email)
              .password(passwordEncoder.encode(password))
              .role(role)
              .build());
      Users users = usersRepository
          .findByEmail(savedUser.getEmail())
          .orElseThrow();
      return internRepository.save(
          Intern.builder().fonction(fonction).users(users).build());
    } else {
      throw new TechnicalFoundException(
          "Un compte existe Déjà avec cet email :" + email);
    }
  }

  public Intern update(
      Intern intern,
      String name,
      String email,
      RoleEnum role,
      String fonction) throws TechnicalFoundException {
    Optional<Users> users = usersRepository.findByEmail(email);
    if (users.isEmpty() ||
        users.isPresent() &&
            users.get().getId() == intern.getUsers().getId()) {
      intern.getUsers().setName(name);
      intern.getUsers().setEmail(email);
      intern.getUsers().setRole(role);
      usersRepository.save(intern.getUsers());

      intern.setFonction(fonction);
      return internRepository.save(intern);
    } else {
      throw new TechnicalFoundException(
          "Un compte existe Déjà avec cet email :" + email);
    }
  }

  public Intern updateProfile(
      Users users,
      Intern intern,
      String name,
      String fonction) {
    users.setName(name);
    usersRepository.save(users);
    intern.setFonction(fonction);
    return internRepository.save(intern);
  }

  /**
   * Persiste un utilisateur
   *
   * @param intern utilisateur à sauvegarder
   * @return l'utilisateur persité
   */
  public Intern save(Intern intern) {
    return internRepository.save(intern);
  }

  public List<Intern> findAll() {
    return internRepository.findAll();
  }
}
