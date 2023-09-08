package com.esgi.pa.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.EventUsers;

public interface EventUsersRepository extends JpaRepository<EventUsers, Long> {
}
