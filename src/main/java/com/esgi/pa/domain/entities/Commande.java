package com.esgi.pa.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.esgi.pa.domain.enums.TypeCommandeEnum;

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
@Table(name = "commandes")
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
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private Client creator;
    @ManyToOne
    @JoinColumn(name = "livreur_id", referencedColumnName = "id")
    private Intern livreur;
}
