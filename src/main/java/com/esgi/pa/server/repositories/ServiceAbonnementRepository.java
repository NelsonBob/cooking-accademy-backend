package com.esgi.pa.server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.ServiceAbonnement;

/**
 * Interface de persistence pour les Cours
 */
public interface ServiceAbonnementRepository extends JpaRepository<ServiceAbonnement, Long> {
    Optional<ServiceAbonnement> findByName(String name);
    List<ServiceAbonnement> findByStatusOrderById(Boolean status);
}
