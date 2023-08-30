package com.esgi.pa.domain.entities;

import javax.persistence.Column;
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
 * Entité représentant un materiel
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "materiels")
public class Materiel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    private Integer quantity;
    private Float price;
    private String imgPath;
    @Column(columnDefinition = "text")
    private String gallerie;
    @ManyToOne
    @JoinColumn(name = "categorieMateriel_id", referencedColumnName = "id")
    @JsonIgnoreProperties({ "categorieMateriel" })
    private CategorieMateriel categorieMateriel;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Intern creator;
    private Boolean status;
}
