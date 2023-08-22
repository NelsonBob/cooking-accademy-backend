package com.esgi.pa.domain.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

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
public class Intern {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String fonction;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  @JsonIgnoreProperties({"users"})
  private Users users;

  @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
  private List<Cour> cours = new ArrayList<>();

  @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
  private List<OptionAbonnement> optionAbonnements = new ArrayList<>();

  @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
  private List<ServiceAbonnement> serviceAbonnements = new ArrayList<>();

  @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
  private List<Salle> salles = new ArrayList<>();

  @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
  private List<CategorieMateriel> categorieMateriels = new ArrayList<>();

  @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
  private List<Materiel> materiels = new ArrayList<>();

  @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
  private List<Repas> repas = new ArrayList<>();
  @OneToMany(mappedBy = "livreur", fetch = FetchType.LAZY)
  private List<Commande> commandes = new ArrayList<>();
}
