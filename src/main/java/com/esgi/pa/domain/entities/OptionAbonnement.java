package com.esgi.pa.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Entité représentant un cours de cuisine
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OptionAbonnement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    /*
    {
      "idservice":"1",
      "idoption":"2",
      "icon":true,
      "valueicon":true,
      "description":true,
      "descriptionvalue":"hello"
   }*/
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Intern creator;
}
