package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.event.GetEventResponse;
import com.esgi.pa.domain.entities.Event;

public interface EventMapper {
  static GetEventResponse toGetEventResponse(Event event) {
    return new GetEventResponse(
        event.getId(),
        event.getTitle(),
        event.getStart(),
        event.getEnd(),
        UserMapper.toGetUserResponse(event.getCreator()));
  }

  static List<GetEventResponse> toGetEventResponse(List<Event> entities) {
    return entities
        .stream()
        .map(EventMapper::toGetEventResponse)
        .distinct()
        .toList();
  }

}