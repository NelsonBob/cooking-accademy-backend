package com.esgi.pa.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
