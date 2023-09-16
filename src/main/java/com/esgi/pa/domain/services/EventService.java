package com.esgi.pa.domain.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Evenement;
import com.esgi.pa.domain.entities.EventUsers;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.StatusReservationEnum;
import com.esgi.pa.domain.enums.TypeEventEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.EventRepository;
import com.esgi.pa.server.repositories.EventUsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

  private final EventRepository eventRepository;
  private final EventUsersRepository eventUsersRepository;

  public void createReservation(
    Users users,
    String title,
    String start,
    String end,
    long elementId,
    String imgPath
  ) throws TechnicalFoundException {
    eventRepository.save(
      Evenement
        .builder()
        .title(title)
        .startDate(start)
        .endDate(end)
        .elementId(elementId)
        .users(users)
        .typeEventEnum(TypeEventEnum.Reservation)
        .imgPath(imgPath)
        .statusEvent(StatusReservationEnum.Pending)
        .build()
    );
  }

  public void updateEvent(
    Evenement evenement,
    StatusReservationEnum statusReservationEnum
  ) {
    evenement.setStatusEvent(statusReservationEnum);
    eventRepository.save(evenement);
  }

  public List<Evenement> findAll() {
    return eventRepository.findAll();
  }

  public List<Evenement> findEvent(
    TypeEventEnum typeEventEnum,
    Long elementId
  ) {
    return eventRepository.findByTypeEventEnumAndElementId(
      typeEventEnum,
      elementId
    );
  }

  public List<Evenement> findEventAll(TypeEventEnum typeEventEnum) {
    return eventRepository.findByTypeEventEnumIsNot(typeEventEnum);
  }

  public Evenement getById(Long id) throws TechnicalNotFoundException {
    return eventRepository
      .findById(id)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          HttpStatus.NOT_FOUND,
          "No event found with following id : "
        )
      );
  }

  public void delete(Evenement event) {
    eventRepository.delete(event);
  }

  public List<Evenement> findEventList(Users creator) {
    return eventRepository.findByusers(creator);
  }

  public void createEvent(
    Users users,
    String title,
    String start,
    String end,
    List<Users> list
  ) throws TechnicalFoundException {
    Evenement evenement = eventRepository.save(
      Evenement
        .builder()
        .title(title)
        .startDate(start)
        .endDate(end)
        .users(users)
        .typeEventEnum(TypeEventEnum.Meeting)
        .statusEvent(StatusReservationEnum.Pending)
        .build()
    );

    list.forEach(el -> {
      eventUsersRepository.save(
        EventUsers
          .builder()
          .users(el)
          .event(evenement)
          .statusEvent(StatusReservationEnum.Pending)
          .build()
      );
    });
  }
}
