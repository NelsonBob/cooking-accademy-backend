package com.esgi.pa.domain.entities;

import java.util.ArrayList;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.esgi.pa.domain.enums.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * Entité représentant un utilisateur
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true)
  private String name;

  @Column(unique = true)
  private String email;

  private String password;
  private String imgPath;

  @With
  @Enumerated(EnumType.STRING)
  private RoleEnum role;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "package_id")
  private ServiceAbonnement serviceAbonnement;

  private Date dateSuscription;

  private Date dateExpiration;

  @Builder.Default
  @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
  private List<PaymentCommande> payments = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
  private List<PaymentAbonnement> paymentAbonnements = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
  private List<Evenement> events = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
  private List<EventUsers> eventUsers = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
