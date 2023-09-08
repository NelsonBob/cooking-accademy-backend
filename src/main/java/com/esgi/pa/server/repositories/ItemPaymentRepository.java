package com.esgi.pa.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.ItemPayment;

public interface ItemPaymentRepository extends JpaRepository<ItemPayment, Long> {
}
