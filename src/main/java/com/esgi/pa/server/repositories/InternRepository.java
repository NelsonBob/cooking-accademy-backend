package com.esgi.pa.server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Users;

/**
 * Interface de persistence pour les CategorieMateriel
 */
public interface InternRepository extends JpaRepository<Intern, Long> {
    Optional<Intern> findByUsers(Users users);
    boolean existsByUsers(Users users);
}
