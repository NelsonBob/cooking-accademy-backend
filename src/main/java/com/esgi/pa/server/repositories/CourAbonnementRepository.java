package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Cour;
import com.esgi.pa.domain.entities.CourAbonnement;
import com.esgi.pa.domain.entities.Users;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourAbonnementRepository
  extends JpaRepository<CourAbonnement, Long> {
  @Query(
    "SELECT COUNT(ca) FROM CourAbonnement ca WHERE ca.users = :users AND ca.cour = :cour AND DATE(ca.dayDate) = DATE(:dayDate)"
  )
  long countByUsersAndCourAndDayDate(
    @Param("users") Users users,
    @Param("cour") Cour cour,
    @Param("dayDate") Date dayDate
  );

  @Query(
    "SELECT COUNT(ca) FROM CourAbonnement ca WHERE ca.users = :users AND DATE(ca.dayDate) = DATE(:dayDate)"
  )
  long countByUsersAndDayDate(
    @Param("users") Users users,
    @Param("dayDate") Date dayDate
  );
}
