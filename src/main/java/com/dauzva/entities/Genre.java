package com.dauzva.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "genre")
@Getter @Setter
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    // Inverse side of the Movie<->Genre many-to-many
    // "mappedBy = "genres"" means the "genres" field in Movie owns the relationship
    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies;
}