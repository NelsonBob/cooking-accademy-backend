package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.eventUsers.GetEventUsersResponse;
import com.esgi.pa.api.dtos.responses.eventUsers.GetUserEventResponse;
import com.esgi.pa.domain.entities.EventUsers;
import java.util.List;

public interface EventUsersMapper {
  static GetEventUsersResponse toGetEventUserResponse(EventUsers event) {
    String im = "";
    if (event.getEvent().getImgPath() != "") im = event.getEvent().getImgPath();
    return new GetEventUsersResponse(
      event.getEvent().getId(),
      event.getEvent().getTitle(),
      event.getEvent().getStartDate(),
      event.getEvent().getEndDate(),
      UserMapper.toGetUserResponse(event.getUsers()),
      im,
      event.getEvent().getDescription(),
      event.getEvent().getUsers().getId(),
      event.getId(),
      event.getStatusEvent(),
      event.getEvent().getTypeEventEnum()
    );
  }

  static List<GetEventUsersResponse> toGetEventUserResponse(
    List<EventUsers> entities
  ) {
    return entities
      .stream()
      .map(EventUsersMapper::toGetEventUserResponse)
      .distinct()
      .toList();
  }

  static GetUserEventResponse toGetUserEventResponse(EventUsers event) {
    return new GetUserEventResponse(
      event.getUsers().getId(),
      event.getUsers().getName(),
      event.getUsers().getEmail(),
      event.getStatusEvent()
    );
  }

  static List<GetUserEventResponse> toGetUserEventResponse(
    List<EventUsers> entities
  ) {
    return entities
      .stream()
      .map(EventUsersMapper::toGetUserEventResponse)
      .distinct()
      .toList();
  }
}
