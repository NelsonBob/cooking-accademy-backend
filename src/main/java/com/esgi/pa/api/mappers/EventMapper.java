package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.event.GetEventResponse;
import com.esgi.pa.domain.entities.Evenement;
import java.util.List;

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
}
