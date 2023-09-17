package com.esgi.pa.api.dtos.responses.eventUsers;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.esgi.pa.domain.enums.StatusReservationEnum;
import com.esgi.pa.domain.enums.TypeEventEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetEventUsersResponse(
  Long id,
  String title,
  String start,
  String end,
  GetUserResponse user,
  String imgPath,
  String description,
  Long idcreator,
  Long ideventuser,
  StatusReservationEnum status,
  TypeEventEnum typeEventEnum
) {}
