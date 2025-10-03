package com.example.demo.domain;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.domain.entities.Airline;
import com.example.demo.domain.repositories.AirlineRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AirlineRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private AirlineRepository airlineRepository;

    @Test
    void testFindByCode() {
        // Arrange
        Airline airline = new Airline(null, "AV", "Avianca", null);
        airlineRepository.save(airline);

        // Act
        Optional<Airline> foundAirline = airlineRepository.findByCode("AV");

        // Assert
        assertThat(foundAirline).isPresent();
        assertThat(foundAirline.get().getName()).isEqualTo("Avianca");
    }
}