package com.example.demo.domain;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.domain.entities.Airport;
import com.example.demo.domain.repositories.AirportRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AirportRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private AirportRepository airportRepository;

    @Test
    void testFindByCode() {
        // Arrange
        Airport airport = new Airport(null, "JFK", "John F. Kennedy", "New York", null, null);
        airportRepository.save(airport);

        // Act
        Optional<Airport> foundAirport = airportRepository.findByCode("JFK");

        // Assert
        assertThat(foundAirport).isPresent();
        assertThat(foundAirport.get().getCity()).isEqualTo("New York");
    }
}