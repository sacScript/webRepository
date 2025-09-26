package com.example.demo.domain;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.domain.entities.*;
import com.example.demo.domain.repositories.AirlineRepository;
import com.example.demo.domain.repositories.AirportRepository;
import com.example.demo.domain.repositories.FlightRepository;
import com.example.demo.domain.repositories.TagRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FlightRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private AirlineRepository airlineRepository;
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TestEntityManager entityManager;

    private Airline airline;
    private Airport origin;
    private Airport destination;
    private Tag tagPromo;
    private Tag tagOferta;

    @BeforeEach
    void setUp() {
        airline = airlineRepository.save(new Airline(null, "AV", "Avianca", null));
        origin = airportRepository.save(new Airport(null, "BOG", "El Dorado", "Bogota", null, null));
        destination = airportRepository.save(new Airport(null, "JFK", "John F. Kennedy", "New York", null, null));
        tagPromo = tagRepository.save(new Tag(null, "Promo", null));
        tagOferta = tagRepository.save(new Tag(null, "Oferta", null));
    }

    @Test
    void testSearchFlightsWithDetails() {
        // Arrange
        OffsetDateTime from = OffsetDateTime.of(2025, 10, 20, 10, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime to = from.plusDays(1);

        Flight flight = new Flight(null, "AV020", from.plusHours(2), from.plusHours(8), airline, destination, destination, null, null, null);
        flight.setAirline(airline);
        flight.setOrigin(origin);
        flight.setDestination(destination);
        flight.setTags(Set.of(tagPromo));
        flightRepository.save(flight);

        entityManager.flush();
        entityManager.clear();

        // Act
        List<Flight> results = flightRepository.searchFlightsWithDetails("BOG", "JFK", from, to);

        // Assert
        assertThat(results).hasSize(1);
        Flight resultFlight = results.get(0);
        // Verifica que las asociaciones se cargaron (no lanzará LazyInitializationException)
        assertThat(resultFlight.getAirline().getName()).isEqualTo("Avianca");
        assertThat(resultFlight.getOrigin().getCity()).isEqualTo("Bogota");
        assertThat(resultFlight.getTags()).hasSize(1);
    }

    @Test
    void testFindFlightsWithAllTags() {
        // Arrange
        OffsetDateTime departure = OffsetDateTime.now();
        // Vuelo 1: tiene AMBAS etiquetas
        Flight flightWithAllTags = new Flight(null, "AV001", departure, departure.plusHours(5), airline, destination, destination, null, null, null);
        flightWithAllTags.setAirline(airline);
        flightWithAllTags.setOrigin(origin);
        flightWithAllTags.setDestination(destination);
        flightWithAllTags.setTags(Set.of(tagPromo, tagOferta));
        flightRepository.save(flightWithAllTags);

        // Vuelo 2: tiene SOLO UNA etiqueta
        Flight flightWithOneTag = new Flight(null, "AV002", departure, departure.plusHours(5), airline, destination, destination, null, null, null);
        flightWithOneTag.setAirline(airline);
        flightWithOneTag.setOrigin(origin);
        flightWithOneTag.setDestination(destination);
        flightWithOneTag.setTags(Set.of(tagPromo));
        flightRepository.save(flightWithOneTag);

        // Act
        List<String> requiredTags = List.of("Promo", "Oferta");
        List<Flight> results = flightRepository.findFlightsWithAllTags(requiredTags, requiredTags.size());

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getNumber()).isEqualTo("AV001");
    }
}