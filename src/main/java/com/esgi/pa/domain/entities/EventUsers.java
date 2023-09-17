package com.esgi.pa.domain.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.esgi.pa.domain.enums.StatusReservationEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_users")
public class EventUsers {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @With
  @Enumerated(EnumType.STRING)
  private StatusReservationEnum statusEvent;

  @ManyToOne
  @JoinColumn(name = "event_id", referencedColumnName = "id")
 @JsonIgnoreProperties({ "event" })
  private Evenement event;

  @ManyToOne
  @JoinColumn(name = "creator_id", referencedColumnName = "id")
  @JsonIgnoreProperties({ "users" })
  private Users users;
}
