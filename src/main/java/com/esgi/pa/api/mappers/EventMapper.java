package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.event.GetEventIdResponse;
import com.esgi.pa.api.dtos.responses.event.GetEventResponse;
import com.esgi.pa.domain.entities.Evenement;

public interface EventMapper {
  static GetEventResponse toGetEventResponse(Evenement event) {
    String im = "";
    if (event.getImgPath() != "") im = event.getImgPath();
    
    return new GetEventResponse(
      event.getId(),
      event.getTitle(),
      event.getStartDate(),
      event.getEndDate(),
      UserMapper.toGetUserResponse(event.getUsers()),
      im,
      event.getDescription(),
      event.getUsers().getId(),
      event.getStatusEvent(),
      event.getTypeEventEnum()
    );
  }

  static List<GetEventResponse> toGetEventResponse(List<Evenement> entities) {
    return entities
      .stream()
      .map(EventMapper::toGetEventResponse)
      .distinct()
      .toList();
  }

  static GetEventIdResponse toGetEventIdResponse(Evenement event) {
    return new GetEventIdResponse(
      event.getId(),
      event.getTitle(),
      event.getStartDate(),
      event.getEndDate(),
      event.getDescription(),
      UserMapper.toGetUserResponse(event.getUsers()),
      EventUsersMapper.toGetUserEventResponse(event.getEventUsers())
    );
  }

  static List<GetEventIdResponse> toGetEventIdResponse(
    List<Evenement> entities
  ) {
    return entities
      .stream()
      .map(EventMapper::toGetEventIdResponse)
      .distinct()
      .toList();
  }
}
