package com.dauzva.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "cinema")
@Getter @Setter
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Field "name" → column "name" — names match, no @Column needed
    private String name;
    private String city;
    private String address;

    // ONE cinema has MANY halls
    // "mappedBy = "cinema"" means the "cinema" field in Hall is the owning side
    @OneToMany(mappedBy = "cinema", fetch = FetchType.LAZY)
    private List<Hall> halls;
}