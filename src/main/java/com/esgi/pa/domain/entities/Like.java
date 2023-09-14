package com.esgi.pa.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "likes")
public class Like {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private  Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;
}