package com.esgi.pa.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Cour;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Message;
import com.esgi.pa.domain.entities.Users;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatCourAndCreatorOrChatCourCreatorOrderByIdDesc(Cour cour,Users sender, Intern receive);

}