package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.requests.event.CreateEventRequest;
import com.esgi.pa.api.dtos.responses.event.GetEventResponse;
import com.esgi.pa.api.mappers.EventMapper;
import com.esgi.pa.domain.entities.Event;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.enums.TypeEventEnum;
import com.esgi.pa.domain.exceptions.NotAuthorizationRessourceException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.EventService;
import com.esgi.pa.domain.services.UserService;
import com.esgi.pa.domain.services.util.UtilService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

/**
 * Contient les routes des Service Abonnement
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
@Api(tags = "Event API")
public class EventResource {

  private final UserService userService;
  private final EventService eventService;

  @PostMapping(value = "{id}")
  @ResponseStatus(CREATED)
  public List<GetEventResponse> create(
      @Valid @RequestBody CreateEventRequest request,
      @PathVariable Long id)
      throws TechnicalFoundException, TechnicalNotFoundException, JsonProcessingException {
    Users users = userService.getById(id);
    eventService.createReservation(
        users,
        request.title(),
        request.start(),
        request.end(),
        request.elementId(),
        request.imgPath());
    return EventMapper.toGetEventResponse(eventService.findEvent(TypeEventEnum.Reservation, request.elementId()));
  }

  @GetMapping("{id}")
  public List<GetEventResponse> listAllEvent(@PathVariable Long id)
      throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (UtilService.isGranted(users.getRole(), Arrays.asList(RoleEnum.Admin)))
      return EventMapper.toGetEventResponse(eventService.findAll());
    else
      return EventMapper.toGetEventResponse(eventService.findEventList(users));

  }

  @DeleteMapping(value = "{idk}/element/{id}")
  @ResponseStatus(OK)
  public List<GetEventResponse> delete(@PathVariable Long idk, @PathVariable Long id)
      throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Event event = eventService.getById(idk);
    eventService.delete(event);
    return EventMapper.toGetEventResponse(eventService.findEvent(TypeEventEnum.Reservation, id));
  }
}
