package com.esgi.pa.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Evenement;
import com.esgi.pa.domain.entities.Users;
import com.esgi.pa.domain.enums.TypeEventEnum;

public interface EventRepository extends JpaRepository<Evenement, Long> {
    List<Evenement> findByTypeEventEnumAndElementId(TypeEventEnum typeEventEnum, long elementId);
    
    List<Evenement> findByTypeEventEnumIsNot(TypeEventEnum typeEventEnum);

    List<Evenement> findByusers(Users creator);
}
