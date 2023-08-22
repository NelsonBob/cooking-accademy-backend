package com.esgi.pa.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.OptionServiceAbonnement;

/**
 * Interface de persistence pour les Cours
 */
public interface OptionServiceAbonnementRepository extends JpaRepository<OptionServiceAbonnement, Long> {
}
