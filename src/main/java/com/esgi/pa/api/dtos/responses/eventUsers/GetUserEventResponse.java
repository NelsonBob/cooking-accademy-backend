package com.esgi.pa.api.dtos.responses.eventUsers;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.domain.enums.StatusReservationEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetUserEventResponse(
  Long id,
  String name,
  String email,
  StatusReservationEnum status
) {}
