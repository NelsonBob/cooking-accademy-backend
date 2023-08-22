package com.esgi.pa.domain.entities;

import com.esgi.pa.domain.enums.RoleEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Entité représentant un utilisateur
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Users implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(unique = true)
  private String name;
  @Column(unique = true)
  private String email;
  private String password;
  @With
  @Enumerated(EnumType.STRING)
  private RoleEnum role;
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
