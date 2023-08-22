package com.esgi.pa.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Materiel;

/**
 * Interface de persistence pour les CategorieMateriel
 */
public interface MaterielRepository extends JpaRepository<Materiel, Long> {
}
