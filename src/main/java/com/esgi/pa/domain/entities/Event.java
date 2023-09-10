package com.esgi.pa.domain.entities;

import java.util.ArrayList;
import java.util.List;

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

import com.esgi.pa.domain.enums.TypeEventEnum;

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
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String start;
    private String end;
    private long elementId;// si c'est un planning de cour c'est id du cour
    private String imgPath;
    @With
    @Enumerated(EnumType.STRING)
    private TypeEventEnum typeEventEnum;
    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private Users creator;
    @Builder.Default
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<EventUsers> eventUsers = new ArrayList<>();
}
