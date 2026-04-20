package com.dauzva.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "hall")
@Getter @Setter
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer capacity;

    // MANY halls belong to ONE cinema
    // @JoinColumn tells JPA which column in THIS table is the foreign key
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
}