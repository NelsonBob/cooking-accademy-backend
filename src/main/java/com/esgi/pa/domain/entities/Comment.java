package com.esgi.pa.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @Column(name = "description", columnDefinition = "text")
  private String description;

  @ManyToOne
  @JoinColumn(name = "post_id", referencedColumnName = "id")
  @JsonIgnoreProperties({ "post" })
  private Post post;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private Users author;
}
