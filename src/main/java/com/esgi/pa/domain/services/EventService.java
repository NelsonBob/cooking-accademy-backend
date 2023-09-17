package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Evenement;
import com.esgi.pa.domain.entities.EventUsers;
import com.esgi.pa.domain.entities.Salle;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.StatusReservationEnum;
import com.esgi.pa.domain.enums.TypeEventEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.repositories.EventRepository;
import com.esgi.pa.server.repositories.EventUsersRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    String imgPath,
    Salle salle1
  ) throws TechnicalFoundException {
    eventRepository.save(
      Evenement
        .builder()
        .title(title)
        .startDate(start)
        .endDate(end)
        .elementId(elementId)
        .description(salle1.getName())
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
    return eventRepository.findByUsers(creator);
  }

  public List<Evenement> findEventListAdmin(Users creator) {
    List<Evenement> evenements1 = eventRepository.findByTypeEventEnumAndUsersIsNot(
      TypeEventEnum.Reservation,
      creator
    );
    List<Evenement> evenements3 = eventRepository.findByTypeEventEnumAndUsers(
      TypeEventEnum.Reservation,
      creator
    );
    List<Evenement> evenements2 = eventRepository.findByTypeEventEnumAndUsers(
      TypeEventEnum.Meeting,
      creator
    );
    List<Evenement> combinedList = new ArrayList<>();
    combinedList.addAll(evenements1);
    combinedList.addAll(evenements2);
    combinedList.addAll(evenements3);

    return combinedList;
  }

  public void createEvent(
    Users users,
    String title,
    String start,
    String end,
    String description,
    List<Users> list
  ) throws TechnicalFoundException {
    Evenement evenement = eventRepository.save(
      Evenement
        .builder()
        .title(title)
        .startDate(start)
        .endDate(end)
        .users(users)
        .description(description)
        .typeEventEnum(TypeEventEnum.Meeting)
        .statusEvent(StatusReservationEnum.Confirm)
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
