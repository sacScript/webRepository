package com.example.demo.services;
import com.example.demo.api.dto.PassengerDtos.CreatePassengerDTO;
import com.example.demo.api.dto.PassengerDtos.UpdatePassengerDTO;
import com.example.demo.domain.entities.Passenger;
import com.example.demo.domain.entities.PassengerProfile;
import com.example.demo.domain.repositories.PassengerProfileRepository;
import com.example.demo.domain.repositories.PassengerRepository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PassengerServiceImplTest {

    @Mock
    private PassengerRepository passengerRepo;

    @Mock
    private PassengerProfileRepository profileRepo;

    @InjectMocks
    private PassengerServiceImpl service;

    private Passenger passenger;
    private PassengerProfile profile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        profile = PassengerProfile.builder()
                .id(10L)
                .phone("123456789")
                .countryCode("CO")
                .build();

        passenger = Passenger.builder()
                .id(1L)
                .fullName("Luis Fernando")
                .email("luis@example.com")
                .profile(profile)
                .build();
    }

    @Test
    void testCreateSuccess() {
        CreatePassengerDTO dto = new CreatePassengerDTO("Luis Fernando", "luis@example.com", 10L);

        when(profileRepo.findById(10L)).thenReturn(Optional.of(profile));
        when(passengerRepo.save(any(Passenger.class))).thenReturn(passenger);

        var response = service.create(dto);

        assertNotNull(response);
        assertEquals("Luis Fernando", response.fullName());
        assertEquals("luis@example.com", response.email());
        assertEquals(10L, response.passengerProfileId());
    }

    @Test
    void testCreateProfileNotFound() {
        CreatePassengerDTO dto = new CreatePassengerDTO("Luis", "luis@example.com", 99L);
        when(profileRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.create(dto));
    }

    @Test
    void testGetSuccess() {
        when(passengerRepo.findById(1L)).thenReturn(Optional.of(passenger));

        var response = service.get(1L);

        assertEquals(1L, response.id());
        assertEquals("Luis Fernando", response.fullName());
    }

    @Test
    void testGetNotFound() {
        when(passengerRepo.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.get(2L));
    }

    @Test
    void testUpdateSuccess() {
        UpdatePassengerDTO dto = new UpdatePassengerDTO(1L, "Luis F.", "fernando@example.com", 10L);

        when(passengerRepo.findById(1L)).thenReturn(Optional.of(passenger));
        when(profileRepo.findById(10L)).thenReturn(Optional.of(profile));
        when(passengerRepo.save(any(Passenger.class))).thenReturn(passenger);

        var response = service.update(1L, dto);

        assertNotNull(response);
        assertEquals("Luis F.", response.fullName());
        assertEquals("fernando@example.com", response.email());
        assertEquals(10L, response.passengerProfileId());
    }

    @Test
    void testUpdatePassengerNotFound() {
        UpdatePassengerDTO dto = new UpdatePassengerDTO(99L, "Luis F.", "fernando@example.com", 10L);

        when(passengerRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.update(99L, dto));
    }

    @Test
    void testDeleteSuccess() {
        when(passengerRepo.existsById(1L)).thenReturn(true);
        doNothing().when(passengerRepo).deleteById(1L);

        service.delete(1L);

        verify(passengerRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(passengerRepo.existsById(2L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.delete(2L));
    }

    @Test
    void testListPassengers() {
        when(passengerRepo.findAll()).thenReturn(List.of(passenger));

        var responses = service.list();

        assertEquals(1, responses.size());
        assertEquals("Luis Fernando", responses.get(0).fullName());
    }
}