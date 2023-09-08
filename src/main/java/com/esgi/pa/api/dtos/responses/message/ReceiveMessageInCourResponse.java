package com.esgi.pa.api.dtos.responses.message;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * DTO de réponse à une requête
 *
 * @param senderName  nom de l'envoyeur
 * @param message     contenu du message
 * @param currentDate date d'envoi
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record ReceiveMessageInCourResponse(
        @NotBlank(message = "Sender Id is required") Long senderUser,
        @NotBlank(message = "Sender senderName Id is required") String senderName,
        @NotNull(message = "Message Id is required") String message,
        String currentDate) {
}