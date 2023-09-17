package com.esgi.pa.api.dtos.responses.event;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.esgi.pa.domain.enums.TypeEventEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetFuturResponse(
  Long id,
  String title,
  String start,
  String end,
  GetUserResponse user,
  String imgPath,
  String description,
  Long idcreator,
  Boolean isRegister,
  TypeEventEnum typeEventEnum
) {}
