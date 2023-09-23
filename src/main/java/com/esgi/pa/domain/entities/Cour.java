package com.esgi.pa.domain.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 * Entité représentant un cours de cuisine
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cours")
public class Cour {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @Column(columnDefinition = "text")
  private String description;

  private String imgPath;

  @Column(columnDefinition = "text")
  private String videoLink;

  @Column(columnDefinition = "text")
  private String contentCour;

  private Boolean status;
  private Boolean isVideoLocal;

  @ManyToOne
  @JoinColumn(name = "creator_id", referencedColumnName = "id")
  private Intern creator;

  @Builder.Default
  @OneToMany(mappedBy = "cour", fetch = FetchType.LAZY)
  private List<CourAbonnement> courAbonnements = new ArrayList<>();
}
