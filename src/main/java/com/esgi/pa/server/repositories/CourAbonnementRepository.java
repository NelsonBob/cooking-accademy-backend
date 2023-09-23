package com.esgi.pa.server.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Cour;
import com.esgi.pa.domain.entities.CourAbonnement;
import com.esgi.pa.domain.entities.Users;

public interface CourAbonnementRepository
  extends JpaRepository<CourAbonnement, Long> {
  long countByUsersAndCourAndDayDate(Users users, Cour cour, Date dayDate);
  long countByUsersAndDayDate(Users users, Date dayDate);
}
