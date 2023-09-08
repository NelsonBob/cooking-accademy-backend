package com.esgi.pa.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}