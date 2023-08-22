package com.esgi.pa.server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Salle;

/**
 * Interface de persistence pour les Cours
 */
public interface SalleRepository extends JpaRepository<Salle, Long> {
 Optional<Salle> findByName(String name);
    List<Salle> findByStatus(Boolean status);
}
