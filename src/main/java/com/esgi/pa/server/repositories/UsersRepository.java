package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.RoleEnum;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de persistence pour les utilisateurs
 */
public interface UsersRepository extends JpaRepository<Users, Long> {
  Optional<Users> findByEmail(String email);

  List<Users> findByRole(RoleEnum roleEnum);
  List<Users> findByIdIsNot(Long id);
}
