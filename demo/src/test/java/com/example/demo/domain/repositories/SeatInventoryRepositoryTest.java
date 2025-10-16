package com.example.demo.domain.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.domain.entities.Flight;
import com.example.demo.domain.entities.SeatInventory;
import com.example.demo.domain.enums.Cabin;

class SeatInventoryRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private SeatInventoryRepository seatInventoryRepository;
    @Autowired
    private FlightRepository flightRepository;

    @Test
    void testHasAvailableSeats() {
        // Arrange
        Flight flight = flightRepository.save(new Flight(null, "LH456", OffsetDateTime.now(), OffsetDateTime.now().plusHours(10), null, null, null, null, null, null));
        SeatInventory inventory = new SeatInventory();
        seatInventoryRepository.save(inventory);

        // Act & Assert
        // Caso 1: Hay suficientes asientos
        boolean hasEnoughSeats = seatInventoryRepository.hasAvailableSeats(flight.getId(), Cabin.BUSINESS, 5);
        assertThat(hasEnoughSeats).isTrue();

        // Caso 2: No hay suficientes asientos
        boolean hasNotEnoughSeats = seatInventoryRepository.hasAvailableSeats(flight.getId(), Cabin.BUSINESS, 11);
        assertThat(hasNotEnoughSeats).isFalse();

        // Caso 3: Petición para una cabina sin inventario
        boolean noInventory = seatInventoryRepository.hasAvailableSeats(flight.getId(), Cabin.ECONOMY, 1);
        assertThat(noInventory).isFalse();
    }
}