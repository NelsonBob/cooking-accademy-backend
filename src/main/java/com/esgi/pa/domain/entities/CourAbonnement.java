package com.esgi.pa.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CourAbonnement {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "creator_id", referencedColumnName = "id")
  @JsonIgnoreProperties({ "users" })
  private Users users;

  @ManyToOne
  @JoinColumn(name = "cour_id", referencedColumnName = "id")
  @JsonIgnoreProperties({ "cour" })
  private Cour cour;

  private Date dayDate;
}
