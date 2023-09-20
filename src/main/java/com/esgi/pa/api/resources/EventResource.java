package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.esgi.pa.api.dtos.requests.event.CreateEventRequest;
import com.esgi.pa.api.dtos.requests.event.CreateEventWithUsersRequest;
import com.esgi.pa.api.dtos.requests.event.UpdateEventRequest;
import com.esgi.pa.api.dtos.responses.event.GetEventIdResponse;
import com.esgi.pa.api.dtos.responses.event.GetEventResponse;
import com.esgi.pa.api.dtos.responses.event.GetFuturResponse;
import com.esgi.pa.api.mappers.EventMapper;
import com.esgi.pa.domain.entities.Evenement;
import com.esgi.pa.domain.entities.Salle;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.enums.TypeEventEnum;
import com.esgi.pa.domain.exceptions.NotAuthorizationRessourceException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.EventService;
import com.esgi.pa.domain.services.SalleService;
import com.esgi.pa.domain.services.UserService;
import com.esgi.pa.domain.services.util.UtilService;
import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
  private final SalleService salleService;

  @PostMapping(value = "{id}")
  @ResponseStatus(CREATED)
  public List<GetEventResponse> create(
    @Valid @RequestBody CreateEventRequest request,
    @PathVariable Long id
  ) throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    Salle salle1 = salleService.getById(request.elementId());
    eventService.createReservation(
      users,
      request.title(),
      request.start(),
      request.end(),
      request.elementId(),
      request.imgPath(),
      salle1
    );
    return EventMapper.toGetEventResponse(
      eventService.findEvent(TypeEventEnum.Reservation, request.elementId())
    );
  }

  @GetMapping("{id}")
  public List<GetEventResponse> listAllEvent(@PathVariable Long id)
    throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    if (
      UtilService.isGranted(users.getRole(), Arrays.asList(RoleEnum.Admin))
    ) return EventMapper.toGetEventResponse(
      eventService.findEventListAdmin(users)
    ); else {
      return EventMapper.toGetEventResponse(eventService.findEventList(users));
    }
  }

  @DeleteMapping(value = "{idk}/element/{id}")
  @ResponseStatus(OK)
  public List<GetEventResponse> delete(
    @PathVariable Long idk,
    @PathVariable Long id
  ) throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Evenement event = eventService.getById(idk);
    eventService.delete(event);
    return EventMapper.toGetEventResponse(
      eventService.findEvent(TypeEventEnum.Reservation, id)
    );
  }

  @DeleteMapping(value = "{idk}")
  @ResponseStatus(OK)
  public ResponseEntity<?> deleteEvent(@PathVariable Long idk)
    throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Evenement event = eventService.getById(idk);
    eventService.delete(event);
    return ResponseEntity.status(OK).build();
  }

  @PutMapping(value = "{id}")
  @ResponseStatus(OK)
  public List<GetEventResponse> updateEvent(
    @Valid @RequestBody UpdateEventRequest request,
    @PathVariable Long id
  )
    throws TechnicalFoundException, TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    Evenement event = eventService.getById(request.id());
    eventService.updateEvent(event, request.statusEvent());
    if (
      UtilService.isGranted(users.getRole(), Arrays.asList(RoleEnum.Admin))
    ) return EventMapper.toGetEventResponse(
      eventService.findAll()
    ); else return EventMapper.toGetEventResponse(
      eventService.findEventList(users)
    );
  }

  @PostMapping(value = "users/{id}")
  @ResponseStatus(OK)
  public ResponseEntity<?> createEventWithUsers(
    @Valid @RequestBody CreateEventWithUsersRequest request,
    @PathVariable Long id
  ) throws TechnicalFoundException, TechnicalNotFoundException {
    Users users = userService.getById(id);
    List<Users> list2 = new ArrayList<>();
    request
      .users()
      .forEach(el -> {
        try {
          list2.add(userService.getById(el.id()));
        } catch (TechnicalNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      });
    eventService.createEvent(
      users,
      request.title(),
      request.start(),
      request.end(),
      request.description(),
      list2
    );
    return ResponseEntity.status(OK).build();
  }

  @GetMapping("{id}/participants/{idk}")
  public GetEventIdResponse getEventId(
    @PathVariable Long id,
    @PathVariable Long idk
  ) throws TechnicalNotFoundException {
    Users users = userService.getById(id);
    return EventMapper.toGetEventIdResponse(eventService.getById(idk));
  }

  @GetMapping("{id}/futur")
  public List<GetFuturResponse> futurEvent(@PathVariable Long id)
    throws TechnicalNotFoundException {
    Users users = userService.getById(id);
    return EventMapper.toGetFuturResponse(
      eventService.findEventAllFutur(TypeEventEnum.Meeting, users),
      users
    );
  }
}
