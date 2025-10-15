package com.example.demo.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "passenger_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String countryCode;
}
