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
@Table(name = "option_abonnements")
public class OptionAbonnement {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  private Boolean status;

  @ManyToOne
  @JoinColumn(name = "creator_id", referencedColumnName = "id")
  private Intern creator;

  @Builder.Default
  @OneToMany(
    mappedBy = "optionAbonnement",
    fetch = FetchType.LAZY,
    cascade = CascadeType.REMOVE
  )
  private List<OptionServiceAbonnement> optionServiceAbonnement = new ArrayList<>();
}
