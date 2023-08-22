package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.OptionAbonnement;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de persistence pour les Cours
 */
public interface OptionAbonnementRepository
  extends JpaRepository<OptionAbonnement, Long> {
  Optional<OptionAbonnement> findByName(String name);
  List<OptionAbonnement> findByStatus(Boolean status);
}
