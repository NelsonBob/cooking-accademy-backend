package com.esgi.pa.server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Cour;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByCour(Cour cour);

    @Query("SELECT c FROM Chat c LEFT JOIN FETCH c.messages WHERE c.cour = :cour")
    Optional<Chat> findByCourWithMessages(@Param("cour") Cour cour);
}
