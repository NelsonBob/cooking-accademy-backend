package com.esgi.pa.domain.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.EventUsers;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.StatusReservationEnum;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.EventRepository;
import com.esgi.pa.server.repositories.EventUsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventUsersService {

  private final EventRepository eventRepository;
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
