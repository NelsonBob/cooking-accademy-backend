package com.esgi.pa.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Commande;

/**
 * Interface de persistence pour les Commande
 */
public interface CommandeRepository extends JpaRepository<Commande, Long> {
}
