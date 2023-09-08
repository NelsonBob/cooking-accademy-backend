package com.esgi.pa.domain.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esgi.pa.api.dtos.requests.message.SendMessageInCourRequest;
import com.esgi.pa.api.dtos.responses.message.ReceiveMessageInCourResponse;
import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Cour;
import com.esgi.pa.domain.enums.StatusMessageEnum;
import com.esgi.pa.server.repositories.ChatRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service de gestion des chats
 */
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    /**
     * Persite le chat
     *
     * @param cour id numérique du cour
     * @return le chat persisté
     */
    public Chat saveChat(Cour cour) {
        return findChatByCour(cour)
                .orElseGet(() -> chatRepository.save(
                        new Chat(cour)));
    }

    /**
     * Cherche un chat par l'id de son cour
     *
     * @param cour id numérique du cour
     * @return un Optional de chat
     */
    public Optional<Chat> findChatByCour(Cour cour) {
        return chatRepository.findByCour(cour);
    }

    /**
     * Cherche un chat par l'id de son cour avec ses messages
     *
     * @param cour id numérique du cour
     * @return un Optional de chat avec ses messages
     */
    public Optional<Chat> findChatByCourWithMessages(Cour cour) {
        return chatRepository.findByCourWithMessages(cour);
    }

    /**
     * process les messages envoyé dans un cour
     *
     * @param chat                          chat du cour
     * @param receiveMessageInCourResponses information relative au message envoyé
     * @return les messages par cour
     */
    public Map<Long, List<SendMessageInCourRequest>> chatCourResponse(Optional<Chat> chat,
            List<ReceiveMessageInCourResponse> receiveMessageInCourResponses) {
        Map<Long, List<SendMessageInCourRequest>> privateChats = new HashMap<>();
        List<SendMessageInCourRequest> messages = new ArrayList<>();

        receiveMessageInCourResponses.forEach(message -> messages.add(
                new SendMessageInCourRequest(
                        message.senderUser(),
                        chat.get().getCour().getId(),
                        message.message(),
                        message.senderName(),
                        chat.get().getCour().getCreator().getUsers().getName(),
                        StatusMessageEnum.JOIN,
                        message.currentDate())));
        privateChats.put(chat.get().getCour().getId(), messages);
        return privateChats;
    }

}