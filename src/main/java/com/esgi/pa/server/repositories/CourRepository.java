package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Cour;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de persistence pour les Cours
 */
public interface CourRepository extends JpaRepository<Cour, Long> {
  List<Cour> findByStatus(Boolean status);
  List<Cour> findTop3ByOrderByIdDesc();
}
