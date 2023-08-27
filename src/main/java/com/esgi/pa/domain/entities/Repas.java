package com.esgi.pa.domain.entities;

import javax.persistence.*;
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
public class Repas {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  private Float price;
  private String imgPath;

  @Column(columnDefinition = "text")
  private String description;

  @ManyToOne
  @JoinColumn(name = "creator_id")
  private Intern creator;

  private Boolean status;
}
