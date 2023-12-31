package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.enums.TypeAbonnement;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.UsersRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des utilisateurs
 */
@Service
@RequiredArgsConstructor
public class UserService {

  private final UsersRepository usersRepository;

  public List<Users> getByRole(RoleEnum roleEnum) {
    return usersRepository.findByRole(roleEnum);
  }

  /**
   * Récupère un utilisateur par son id
   *
   * @param id id numérique de l'utilsateur
   * @return utilisateur recherché
   * @throws TechnicalNotFoundException si l'utilisateur n'est pas trouvé
   */
  public Users getById(Long id) throws TechnicalNotFoundException {
    return usersRepository
      .findById(id)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No user found with following id : " + id
        )
      );
  }

  public List<Users> listUsers(Long id) throws TechnicalNotFoundException {
    return usersRepository.findByIdIsNot(id);
  }

  public Users getByEmail(String email) throws TechnicalNotFoundException {
    return usersRepository
      .findByEmail(email)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No user found with following id : "
        )
      );
  }

  /**
   * Persiste un utilisateur
   *
   * @param users utilisateur à sauvegarder
   * @return l'utilisateur persité
   */
  public Users save(Users users) {
    return usersRepository.save(users);
  }

  public void updatePicture(Users users, String imgPath) {
    users.setImgPath(imgPath);
    usersRepository.save(users);
  }

  public Date dateSubscription(TypeAbonnement typeAbonnement) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    if (typeAbonnement == TypeAbonnement.MOIS) {
      calendar.add(Calendar.MONTH, 12);
    } else calendar.add(Calendar.YEAR, 1);

    Date nextYearDate = calendar.getTime();
    return nextYearDate;
  }
}
