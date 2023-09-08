package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.message.ReceiveMessageInCourResponse;
import com.esgi.pa.domain.entities.Message;

/**
 * Contient les méthodes pour mapper les entités message du domain vers des dtos
 */
public interface MessageMapper {
    static List<ReceiveMessageInCourResponse> toGetmessageResponse(List<Message> entities) {
        return entities.stream()
                .map(MessageMapper::toGetmessageResponse)
                .distinct()
                .toList();
    }

    static ReceiveMessageInCourResponse toGetmessageResponse(Message message) {
        return new ReceiveMessageInCourResponse(
                message.getCreator().getId(),
                message.getCreator().getName(),
                message.getContent(),
                message.getSentAt());
    }
}