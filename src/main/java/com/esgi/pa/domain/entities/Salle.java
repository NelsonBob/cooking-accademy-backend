package com.esgi.pa.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité représentant une salle
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "salles")
public class Salle {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @Column(columnDefinition = "text")
  private String description;

  private String imgPath;

  @Column(columnDefinition = "text")
  private String gallerie;

  @ManyToOne
  @JoinColumn(name = "creator_id", referencedColumnName = "id")
  private Intern creator;

  private Boolean status;
}
