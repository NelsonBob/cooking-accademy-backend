package com.esgi.pa.domain.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "option_service_abonnements")
public class OptionServiceAbonnement {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "serviceAbonnement_id", referencedColumnName = "id")
  private ServiceAbonnement serviceAbonnement;

  @ManyToOne
  @JoinColumn(name = "optionAbonnement_id", referencedColumnName = "id")
  @JsonIgnoreProperties({ "optionAbonnement" })
  private OptionAbonnement optionAbonnement;

  private Boolean icon;
  private Boolean description;
  private Boolean isValueicon;
  private String descriptionvalue;
}
