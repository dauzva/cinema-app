package com.dauzva.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "screening")
@Getter @Setter
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    private BigDecimal price;

    // A screening shows ONE movie
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    // A screening happens in ONE hall
    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;
}