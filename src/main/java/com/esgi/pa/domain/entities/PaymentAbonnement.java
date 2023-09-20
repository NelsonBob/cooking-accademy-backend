package com.esgi.pa.domain.entities;

import com.esgi.pa.domain.enums.TypeAbonnement;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * Entité représentant les informations d'un Formateur
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments_abonnement")
public class PaymentAbonnement {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private double amount;

  @ManyToOne
  @JoinColumn(name = "creator_id", referencedColumnName = "id")
  private Users users;

  private Date paymentDate;

  @With
  @Enumerated(EnumType.STRING)
  private TypeAbonnement typeAbonnement;

  private String receiptPath;
}
