package com.esgi.pa.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Event;
import com.esgi.pa.domain.enums.TypeEventEnum;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByTypeEventEnumAndElementId(TypeEventEnum typeEventEnum, long elementId);
}
