package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.event.UpdateEventRequest;
import com.esgi.pa.api.dtos.responses.eventUsers.GetEventUsersResponse;
import com.esgi.pa.api.mappers.EventUsersMapper;
import com.esgi.pa.domain.entities.Evenement;
import com.esgi.pa.domain.entities.EventUsers;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.exceptions.NotAuthorizationRessourceException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.EventService;
import com.esgi.pa.domain.services.EventUsersService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contient les routes des Service Abonnement
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/event-users")
@Api(tags = "Event Participant API")
public class EventUsersResource {

  private final UserService userService;
  private final EventUsersService eventUsersService;
  private final EventService eventService;

  @GetMapping("{id}")
  public List<GetEventUsersResponse> listAllEventUsers(@PathVariable Long id)
    throws TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    return EventUsersMapper.toGetEventUserResponse(
      eventUsersService.listEventUsers(users)
    );
  }

  @PutMapping(value = "{id}")
  public List<GetEventUsersResponse> updateEventUser(
    @Valid @RequestBody UpdateEventRequest request,
    @PathVariable Long id
  )
    throws TechnicalFoundException, TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    EventUsers event = eventUsersService.getById(request.id());
    eventUsersService.updateEventUsers(event, request.statusEvent());
    return EventUsersMapper.toGetEventUserResponse(
      eventUsersService.listEventUsers(users)
    );
  }

  @PostMapping(value = "{id}")
  public List<GetEventUsersResponse> checkEventEvent(
    @Valid @RequestBody UpdateEventRequest request,
    @PathVariable Long id
  )
    throws TechnicalFoundException, TechnicalNotFoundException, NotAuthorizationRessourceException {
    Users users = userService.getById(id);
    Evenement event = eventService.getById(request.id());
    eventUsersService.createEventUsers(event, request.statusEvent(), users);
    return EventUsersMapper.toGetEventUserResponse(
      eventUsersService.listEventUsers(users)
    );
  }
}
