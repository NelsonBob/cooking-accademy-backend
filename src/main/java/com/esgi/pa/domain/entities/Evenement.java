package com.esgi.pa.domain.entities;

import com.esgi.pa.domain.enums.StatusReservationEnum;
import com.esgi.pa.domain.enums.TypeEventEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "evenements")
public class Evenement {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String title;
  private String startDate;
  private String endDate;
  private long elementId; // si c'est un planning de cour c'est id du cour
  private String imgPath;
  private String description;

  @With
  @Enumerated(EnumType.STRING)
  private StatusReservationEnum statusEvent;

  @With
  @Enumerated(EnumType.STRING)
  private TypeEventEnum typeEventEnum;

  @ManyToOne
  @JoinColumn(name = "creator_id", referencedColumnName = "id")
  @JsonIgnoreProperties({ "users" })
  private Users users;

  @Builder.Default
  @OneToMany(
    mappedBy = "event",
    fetch = FetchType.LAZY,
    cascade = CascadeType.REMOVE
  )
  private List<EventUsers> eventUsers = new ArrayList<>();
}
