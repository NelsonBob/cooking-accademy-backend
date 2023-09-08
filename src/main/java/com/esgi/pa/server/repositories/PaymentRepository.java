package com.esgi.pa.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Payment;
import com.esgi.pa.domain.entities.Users;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByLivreur(Intern livreur);

    List<Payment> findByUsers(Users users);
}
