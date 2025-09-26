package com.example.demo.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "airports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @OneToMany(mappedBy = "origin")
    @Builder.Default
    private List<Flight> flights = new ArrayList<>();

    @OneToMany(mappedBy = "destination")
    @Builder.Default
    private List<Flight> arrivals = new ArrayList<>();

}