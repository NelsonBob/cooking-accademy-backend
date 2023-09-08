package com.esgi.pa.api.dtos.responses.event;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetEventResponse(
                Long id,
                String title,
                String start,
                String end,
                GetUserResponse user) {
}
