package com.esgi.pa.domain.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Event;
import com.esgi.pa.domain.entities.Users;
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

    public void createReservation(Users users, String title,
            String start, String end, long elementId)
            throws TechnicalFoundException {
        eventRepository.save(Event.builder()
                .title(title)
                .start(start)
                .end(end)
                .elementId(elementId)
                .creator(users)
                .typeEventEnum(TypeEventEnum.Reservation)
                .build());
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public List<Event> findEvent(TypeEventEnum typeEventEnum, Long elementId) {
        return eventRepository.findByTypeEventEnumAndElementId(typeEventEnum, elementId);
    }

    public Event getById(Long id) throws TechnicalNotFoundException {
        return eventRepository
                .findById(id)
                .orElseThrow(() -> new TechnicalNotFoundException(
                        HttpStatus.NOT_FOUND,
                        "No event found with following id : "));
    }

    public void delete(Event event) {
        eventRepository.delete(event);
    }

    public List<Event> findEventList(Users creator) {
        return eventRepository.findByCreator(creator);
    }

}
