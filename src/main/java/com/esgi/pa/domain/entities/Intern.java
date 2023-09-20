package com.esgi.pa.domain.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité représentant les informations d'un Formateur
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "interns")
public class Intern {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String fonction;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  @JsonIgnoreProperties({ "users" })
  private Users users;

  @Builder.Default
  @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
  private List<Cour> cours = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
  private List<OptionAbonnement> optionAbonnements = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
  private List<ServiceAbonnement> serviceAbonnements = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
  private List<Salle> salles = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
  private List<CategorieMateriel> categorieMateriels = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
  private List<Materiel> materiels = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
  private List<Repas> repas = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "livreur", fetch = FetchType.LAZY)
  private List<PaymentCommande> commandes = new ArrayList<>();
}
