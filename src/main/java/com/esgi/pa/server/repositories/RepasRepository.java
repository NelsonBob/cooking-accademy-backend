package com.esgi.pa.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Repas;

/**
 * Interface de persistence pour les Cours
 */
public interface RepasRepository extends JpaRepository<Repas, Long> {
}
