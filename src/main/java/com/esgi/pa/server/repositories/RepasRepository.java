package com.esgi.pa.server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Repas;

/**
 * Interface de persistence pour les Cours
 */
public interface RepasRepository extends JpaRepository<Repas, Long> {
    Optional<Repas> findByName(String name);

    List<Repas> findByStatusOrderById(Boolean status);
}
