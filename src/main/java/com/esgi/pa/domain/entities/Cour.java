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
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Intern creator;
}
