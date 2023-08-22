package com.esgi.pa.domain.entities;

import com.esgi.pa.domain.enums.TypeCommandeEnum;
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
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Float price;
    private Float priceTotal;
    private Integer quantity;
    private String adressLivraison;
    private TypeCommandeEnum typeCommandeEnum;
    @Column(columnDefinition = "text")
    private String noteCommande;
    private Long typeId;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Client creator;
    @ManyToOne
    @JoinColumn(name = "livreur_id")
    private Intern livreur;
}
