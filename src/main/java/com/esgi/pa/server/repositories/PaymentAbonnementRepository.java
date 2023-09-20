package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.PaymentAbonnement;
import com.esgi.pa.domain.entities.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentAbonnementRepository
  extends JpaRepository<PaymentAbonnement, Long> {
  List<PaymentAbonnement> findByUsers(Users users);
}
