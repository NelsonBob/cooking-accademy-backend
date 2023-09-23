package com.esgi.pa.domain.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Cour;
import com.esgi.pa.domain.entities.CourAbonnement;
import com.esgi.pa.domain.entities.ServiceAbonnement;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.server.repositories.CourAbonnementRepository;
import com.esgi.pa.server.repositories.ServiceAbonnementRepository;
import com.esgi.pa.server.repositories.UsersRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourAbonnementService {

  private final CourAbonnementRepository courAbonnementRepository;
  private final ServiceAbonnementRepository serviceAbonnementRepository;
  private final UsersRepository usersRepository;

  public Boolean countCourAbonnementByUserAndCourAndDayDate(
    Users user,
    Cour cour
  ) {
    long courDay = courAbonnementRepository.countByUsersAndDayDate(
      user,
      new Date()
    );
    long couruserday = courAbonnementRepository.countByUsersAndCourAndDayDate(
      user,
      cour,
      new Date()
    );
    if (user.getServiceAbonnement() == null) {
      Optional<ServiceAbonnement> service = serviceAbonnementRepository.findByIsDefault(
        Boolean.TRUE
      );
      if (service.isPresent()) {
        user.setServiceAbonnement(service.get());
        usersRepository.save(user);
      }
    }
    if (courDay == 0) {
      courAbonnementRepository.save(
        CourAbonnement
          .builder()
          .cour(cour)
          .users(user)
          .dayDate(new Date())
          .build()
      );
      return true;
    } else if (user.getServiceAbonnement().getName() == "Free") {
      if (courDay == 1 && couruserday == 1) {
        return true;
      } else return false;
    } else if (user.getServiceAbonnement().getName() == "Starter") {
      if (
        couruserday == 1 || courDay == 5 && couruserday == 1
      ) return true; else if (courDay < 5) {
        courAbonnementRepository.save(
          CourAbonnement
            .builder()
            .cour(cour)
            .users(user)
            .dayDate(new Date())
            .build()
        );
        return true;
      } else return false;
    } else return true;
  }
}
