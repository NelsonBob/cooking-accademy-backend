package com.esgi.pa.domain.entities;

import com.esgi.pa.domain.enums.StatusCommandeEnum;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import lombok.With;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_commande")
public class PaymentCommande {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Integer amount;

  @ManyToOne
  @JoinColumn(name = "creator_id", referencedColumnName = "id")
  private Users users;

  private Date paymentDate;
  private String receiptPath;

  @Column(columnDefinition = "text")
  private String noteCommande;

  private String numeroRue;
  private String numeroRueCompl;
  private String ville;
  private String codePostal;
  private Integer telephone;

  @Builder.Default
  private Integer noteAvis = 0;

  @With
  @Enumerated(EnumType.STRING)
  private StatusCommandeEnum statusCommande;

  @ManyToOne
  @JoinColumn(name = "livreur_id", referencedColumnName = "id")
  private Intern livreur;

  @Builder.Default
  @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY,
    cascade = CascadeType.REMOVE)
  private List<ItemPayment> itemPayment = new ArrayList<>();
}
