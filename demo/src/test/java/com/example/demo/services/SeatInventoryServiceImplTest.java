package com.example.demo.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.api.dto.SeatInventoryDtos;
import com.example.demo.domain.entities.Flight;
import com.example.demo.domain.entities.SeatInventory;
import com.example.demo.domain.enums.Cabin;
import com.example.demo.domain.repositories.FlightRepository;
import com.example.demo.domain.repositories.SeatInventoryRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeatInventoryServiceImplTest {

    @Mock
    private SeatInventoryRepository seatInventoryRepository;

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private SeatInventoryServiceImpl seatInventoryService;

    private Flight flight;
    private SeatInventory seatInventory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        flight = Flight.builder()
                .id(1L)
                .number("AV123")
                .build();

        seatInventory = SeatInventory.builder()
                .id(1L)
                .cabin(Cabin.ECONOMY)
                .totalSeats(150)
                .availableSeats(100)
                .flight(flight)
                .build();
    }

    @Test
    void testCreateSuccess() {
        SeatInventoryDtos.CreateSeatInventoryDTO req =
                new SeatInventoryDtos.CreateSeatInventoryDTO(Cabin.ECONOMY, 150, 100, 1L);

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(seatInventoryRepository.save(any(SeatInventory.class))).thenReturn(seatInventory);

        var result = seatInventoryService.create(req);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(Cabin.ECONOMY, result.cabin());
        verify(seatInventoryRepository, times(1)).save(any(SeatInventory.class));
    }

    @Test
    void testCreateFlightNotFound() {
        SeatInventoryDtos.CreateSeatInventoryDTO req =
                new SeatInventoryDtos.CreateSeatInventoryDTO(Cabin.ECONOMY, 150, 100, 99L);

        when(flightRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> seatInventoryService.create(req));
    }

    @Test
    void testGetSuccess() {
        when(seatInventoryRepository.findById(1L)).thenReturn(Optional.of(seatInventory));

        var result = seatInventoryService.get(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    void testGetNotFound() {
        when(seatInventoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> seatInventoryService.get(1L));
    }

    @Test
    void testDeleteSuccess() {
        when(seatInventoryRepository.existsById(1L)).thenReturn(true);

        seatInventoryService.delete(1L);

        verify(seatInventoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(seatInventoryRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> seatInventoryService.delete(1L));
    }

    @Test
    void testUpdateSuccess() {
        SeatInventoryDtos.UpdateSeatInventoryDTO req =
                new SeatInventoryDtos.UpdateSeatInventoryDTO(1L, Cabin.BUSINESS, 120, 80, 1L);

        when(seatInventoryRepository.findById(1L)).thenReturn(Optional.of(seatInventory));
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(seatInventoryRepository.save(any(SeatInventory.class))).thenReturn(seatInventory);

        var result = seatInventoryService.update(1L, req);

        assertNotNull(result);
        assertEquals(Cabin.BUSINESS, result.cabin());
        verify(seatInventoryRepository, times(1)).save(seatInventory);
    }

    @Test
    void testUpdateNotFound() {
        SeatInventoryDtos.UpdateSeatInventoryDTO req =
                new SeatInventoryDtos.UpdateSeatInventoryDTO(1L, Cabin.BUSINESS, 120, 80, 1L);

        when(seatInventoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> seatInventoryService.update(1L, req));
    }

    @Test
    void testListSuccess() {
        when(seatInventoryRepository.findAll()).thenReturn(List.of(seatInventory));

        var result = seatInventoryService.list();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
    }
}
