package com.esgi.pa.server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Evenement;
import com.esgi.pa.domain.entities.EventUsers;
import com.esgi.pa.domain.entities.Users;

public interface EventUsersRepository extends JpaRepository<EventUsers, Long> {
  List<EventUsers> findByUsers(Users users);
  Optional<EventUsers> findByEventAndUsers(Evenement event, Users users);
}
