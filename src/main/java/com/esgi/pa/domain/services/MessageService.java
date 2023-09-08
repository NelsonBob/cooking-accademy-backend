package com.esgi.pa.domain.services;

import java.util.Objects;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.esgi.pa.api.dtos.requests.message.SendMessageInCourRequest;
import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Cour;
import com.esgi.pa.domain.entities.Message;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageRepository messageRepository;
    private final ChatService chatService;

    /**
     * Process le message à transmettre aux utilisateurs dans un cour
     *
     * @param cour    cour auquel le chat appartient
     * @param user    utilisateur à l'origine du message
     * @param message informations relative au message
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     */
    public void dispatchMessage(Cour cour, Users user, SendMessageInCourRequest message)
            throws TechnicalNotFoundException {
        if (!Objects.equals(cour.getCreator().getUsers().getId(), user.getId())) {
            simpMessagingTemplate.convertAndSendToUser(cour.getCreator().getUsers().getName(), "/private", message);
        }
        Chat chat = chatService.saveChat(cour);
        Message message2 = new Message(chat, user, message.message(), message.currentDate());
        messageRepository.save(message2);
    }

}