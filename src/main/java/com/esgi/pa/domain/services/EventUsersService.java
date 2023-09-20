package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Evenement;
import com.esgi.pa.domain.entities.EventUsers;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.StatusReservationEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.EventUsersRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventUsersService {

  private final EventUsersRepository eventUsersRepository;

  public List<EventUsers> listEventUsers(Users users) {
    return eventUsersRepository.findByUsers(users);
  }

  public void updateEventUsers(
    EventUsers eventUsers,
    StatusReservationEnum statusReservationEnum
  ) {
    eventUsers.setStatusEvent(statusReservationEnum);
    eventUsersRepository.save(eventUsers);
  }

  public void createorRemove(
    Evenement evenement,
    StatusReservationEnum statusReservationEnum,
    Users users
  ) throws TechnicalFoundException {
    Optional<EventUsers> event = eventUsersRepository.findByEventAndUsers(
      evenement,
      users
    );
    if (event.isPresent()) eventUsersRepository.delete(
      event.get()
    ); else eventUsersRepository.save(
      EventUsers
        .builder()
        .users(users)
        .event(evenement)
        .statusEvent(statusReservationEnum)
        .build()
    );
  }

  public EventUsers getById(Long id) throws TechnicalNotFoundException {
    return eventUsersRepository
      .findById(id)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No event users found with following id : "
        )
      );
  }
}
