package com.esgi.pa.api.resources;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.esgi.pa.api.dtos.requests.message.SendMessageInCourRequest;
import com.esgi.pa.api.mappers.MessageMapper;
import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Cour;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.ChatService;
import com.esgi.pa.domain.services.CourService;
import com.esgi.pa.domain.services.MessageService;
import com.esgi.pa.domain.services.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class WebsocketResource {
    private final UserService userService;
    private final MessageService messageService;
    private final ChatService chatService;
    private final CourService courService;

    /**
     * Permet le traitement des messages de cours
     *
     * @param message les informations relative au message
     * @return id cours et leurs messages
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     */
    @MessageMapping("/message")
    @SendTo("/chat/cour")
    public Map<Long, List<SendMessageInCourRequest>> processCourMessage(
            SendMessageInCourRequest message) throws TechnicalNotFoundException {
        Cour cour = courService.getById(message.cour());
        Optional<Chat> chat = chatService.findChatByCourWithMessages(cour);
        return chatService.chatCourResponse(
                chat,
                MessageMapper.toGetmessageResponse(chat.get().getMessages()));
    }

    /**
     * Permet le traitment d'un message vers un cour
     *
     * @param message informations relative au message
     * @return le message nouvellement créé
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     */
    @MessageMapping("/private-message")
    public SendMessageInCourRequest recMessage(
            @Payload SendMessageInCourRequest message) throws TechnicalNotFoundException {
        Cour cour = courService.getById(message.cour());
        Users user = userService.getById(message.senderUser());
        messageService.dispatchMessage(
                cour,
                user,
                message);
        return message;
    }
}
