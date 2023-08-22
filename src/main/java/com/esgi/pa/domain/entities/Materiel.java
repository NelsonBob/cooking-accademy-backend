package com.esgi.pa.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Materiel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(columnDefinition = "text")
    private String descritpion;
    private Integer quantity;
    private Float price;
    private String imgPath;
    @Column(columnDefinition = "json")
    private String gallerie;
    @ManyToOne
    @JoinColumn(name = "categorieMateriel_id")
    @JsonIgnoreProperties({"categorieMateriel"})
    private CategorieMateriel categorieMateriel;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Intern creator;
}
