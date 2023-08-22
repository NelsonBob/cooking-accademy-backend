package com.esgi.pa.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.CategorieMateriel;

/**
 * Interface de persistence pour les CategorieMateriel
 */
public interface CategorieMaterielRepository extends JpaRepository<CategorieMateriel, Long> {
}
