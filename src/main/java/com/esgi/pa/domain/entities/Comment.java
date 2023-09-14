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
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "description")
    private String description;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;


    public Comment( String description, Post post, Users user) {
        this.description = description;
        this.post = post;
        this.user = user;
    }
}