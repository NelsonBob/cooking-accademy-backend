package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.event.GetEventResponse;
import com.esgi.pa.domain.entities.EventUsers;
import java.util.List;

public interface EventUsersMapper {
  static GetEventResponse toGetEventUserResponse(EventUsers event) {
    String im = "";
    if (event.getEvent().getImgPath() != "") im = event.getEvent().getImgPath();
    return new GetEventResponse(
      event.getId(),
      event.getEvent().getTitle(),
      event.getEvent().getStartDate(),
      event.getEvent().getEndDate(),
      UserMapper.toGetUserResponse(event.getUsers()),
      im,
      event.getStatusEvent(),
      event.getEvent().getTypeEventEnum()
    );
  }

  static List<GetEventResponse> toGetEventUserResponse(
    List<EventUsers> entities
  ) {
    return entities
      .stream()
      .map(EventUsersMapper::toGetEventUserResponse)
      .distinct()
      .toList();
  }
}
