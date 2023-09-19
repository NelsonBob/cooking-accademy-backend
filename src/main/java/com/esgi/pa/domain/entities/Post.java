package com.esgi.pa.domain.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Column(name = "description", columnDefinition = "text")
  private String description;

  private String datepost;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "user_id")
  Users author;

  private String imgPath;

  @Builder.Default
  @OneToMany(
    mappedBy = "post",
    fetch = FetchType.LAZY,
    cascade = CascadeType.REMOVE
  )
  List<Like> likes = new ArrayList<>();

  @Builder.Default
  @OneToMany(
    mappedBy = "post",
    fetch = FetchType.LAZY,
    cascade = CascadeType.REMOVE
  )
  List<Comment> comments = new ArrayList<>();
}
