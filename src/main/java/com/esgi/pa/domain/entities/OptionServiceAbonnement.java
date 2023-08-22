package com.esgi.pa.domain.entities;

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

/**
 * Entité représentant un cours de cuisine
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OptionServiceAbonnement {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "serviceAbonnement_id")
  private ServiceAbonnement serviceAbonnement;

  @ManyToOne
  @JoinColumn(name = "optionAbonnement_id")
  private OptionAbonnement optionAbonnement;

  private Boolean icon = Boolean.FALSE;
  private Boolean description = Boolean.FALSE;
  private String valueicon;
  private String descriptionvalue;
}
