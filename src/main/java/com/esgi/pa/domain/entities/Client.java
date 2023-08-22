package com.esgi.pa.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant les informations d'un Formateur
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String adress;
    private Integer nbVideoDay;
    private LocalDateTime videoDay;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users users;
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Commande> commandes = new ArrayList<>();
}
