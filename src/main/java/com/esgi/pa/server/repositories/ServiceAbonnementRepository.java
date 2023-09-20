package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.ServiceAbonnement;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de persistence pour les Cours
 */
public interface ServiceAbonnementRepository
  extends JpaRepository<ServiceAbonnement, Long> {
  Optional<ServiceAbonnement> findByName(String name);
  Optional<ServiceAbonnement> findByIsDefault(Boolean isDefault);
  List<ServiceAbonnement> findByStatusOrderById(Boolean status);
}
