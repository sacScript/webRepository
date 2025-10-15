package com.example.demo.domain.repositories;

import com.example.demo.domain.entities.Airline;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AirlineRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private AirlineRepository airlineRepo;

    @Test
    void testFindByCode() {
        // Given
        airlineRepo.save(Airline.builder().name("Avianca").code("AV").build());

        // When
        Optional<Airline> foundAirline = airlineRepo.findByCode("AV");

        // Then
        assertThat(foundAirline).isPresent();
        assertThat(foundAirline.get().getName()).isEqualTo("Avianca");
    }
}