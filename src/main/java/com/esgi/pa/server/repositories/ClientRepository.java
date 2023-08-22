package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Client;

import java.util.Optional;

/**
 * Interface de persistence pour les CategorieMateriel
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUsers(Users users);
}
