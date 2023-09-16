package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.EventUsers;
import com.esgi.pa.domain.entities.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventUsersRepository extends JpaRepository<EventUsers, Long> {
  List<EventUsers> findByUsers(Users users);
}
