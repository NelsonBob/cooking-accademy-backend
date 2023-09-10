package com.esgi.pa.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.esgi.pa.api.dtos.requests.message.SendMessageInCourRequest;
import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Cour;
import com.esgi.pa.domain.entities.Intern;
import com.esgi.pa.domain.entities.Message;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.StatusMessageEnum;
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
        Message message2 = Message
                .builder().chat(chat).creator(user).content(message.message())
                .sentAt(message.currentDate())
                .status(message.status()).build();
        messageRepository.save(message2);
    }

    public List<Message> findMessageIntern(Users users, Intern intern,Cour cour) {
        List<Message> list = messageRepository.findByChatCourAndCreatorOrChatCourCreatorOrderByIdDesc(cour,users, intern);
        list.forEach(el -> {
            el.setStatus(StatusMessageEnum.MESSAGE);
            messageRepository.save(el);
        });
        return list;
    }

    public List<SendMessageInCourRequest> messageCourResponse(List<Message> messagesList) {
        List<SendMessageInCourRequest> messages = new ArrayList<>();

        messagesList.forEach(message -> messages.add(
                new SendMessageInCourRequest(
                        message.getCreator().getId(),
                        message.getChat().getCour().getCreator().getUsers().getId(),
                        message.getContent(),
                        message.getCreator().getName(),
                        message.getChat().getCour().getCreator().getUsers().getName(),
                        message.getStatus(),
                        message.getSentAt())));
        return messages;
    }
}