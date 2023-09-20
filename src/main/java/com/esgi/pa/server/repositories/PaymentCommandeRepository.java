package com.esgi.pa.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.PaymentCommande;
import com.esgi.pa.domain.entities.Users;

public interface PaymentCommandeRepository extends JpaRepository<PaymentCommande, Long> {
    List<PaymentCommande> findByLivreur(Intern livreur);

    List<PaymentCommande> findByUsers(Users users);
}
