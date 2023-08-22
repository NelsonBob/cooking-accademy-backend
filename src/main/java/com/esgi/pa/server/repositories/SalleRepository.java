package com.esgi.pa.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Salle;

/**
 * Interface de persistence pour les Cours
 */
public interface SalleRepository extends JpaRepository<Salle, Long> {
}
