package com.dauzva.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "movie")
@Getter @Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "release_year")
    private Integer releaseYear;

    // MANY movies ↔ MANY genres
    // @JoinTable describes the "movie_genre" join table in the DB
    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),          // FK pointing to THIS table
            inverseJoinColumns = @JoinColumn(name = "genre_id")    // FK pointing to the OTHER table
    )
    private List<Genre> genres;
}