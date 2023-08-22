package com.esgi.pa.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Cour;

/**
 * Interface de persistence pour les Cours
 */
public interface CourRepository extends JpaRepository<Cour, Long> {
}
