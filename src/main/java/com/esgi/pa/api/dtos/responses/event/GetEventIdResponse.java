package com.esgi.pa.api.dtos.responses.event;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.List;

import com.esgi.pa.api.dtos.responses.eventUsers.GetUserEventResponse;
import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetEventIdResponse(
  Long id,
  String title,
  String start,
  String end,
  String description,
  GetUserResponse creator,
  List<GetUserEventResponse> participants
) {}
