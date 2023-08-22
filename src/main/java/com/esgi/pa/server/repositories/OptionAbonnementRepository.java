package com.esgi.pa.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.OptionAbonnement;

/**
 * Interface de persistence pour les Cours
 */
public interface OptionAbonnementRepository extends JpaRepository<OptionAbonnement, Long> {
}
