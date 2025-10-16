package com.example.demo.domain.repositories;

import com.example.demo.domain.entities.Airport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AirportRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private AirportRepository airportRepo;

    @Test
    void testFindByCode() {
        // Given
        airportRepo.save(Airport.builder().code("JFK").name("John F. Kennedy").city("New York").build());

        // When
        Optional<Airport> foundAirport = airportRepo.findByCode("JFK");

        // Then
        assertThat(foundAirport).isPresent();
        assertThat(foundAirport.get().getCity()).isEqualTo("New York");
    }
}