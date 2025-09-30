package com.example.demo.services;


import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.example.demo.api.dto.PassengerProfileDtos.CreatePassengerProfileDTO;
import com.example.demo.api.dto.PassengerProfileDtos.UpdatePassengerProfileDTO;
import com.example.demo.domain.entities.Passenger;
import com.example.demo.domain.entities.PassengerProfile;
import com.example.demo.domain.repositories.PassengerProfileRepository;
import com.example.demo.domain.repositories.PassengerRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PassengerProfileServiceImplTest {

    @Mock
    private PassengerProfileRepository profileRepo;

    @Mock
    private PassengerRepository passengerRepo;

    @InjectMocks
    private PassengerProfileServiceImpl service;

    private Passenger passenger;
    private PassengerProfile profile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        passenger = Passenger.builder()
                .id(1L)
                .fullName("Luis")
                .build();

        profile = PassengerProfile.builder()
                .id(100L)
                .phone("123456789")
                .countryCode("CO")
                .passenger(passenger)
                .build();
    }

    @Test
    void testCreateSuccess() {
        CreatePassengerProfileDTO dto = new CreatePassengerProfileDTO("123456789", "CO", 1L);

        when(passengerRepo.findById(1L)).thenReturn(Optional.of(passenger));
        when(profileRepo.save(any(PassengerProfile.class))).thenReturn(profile);

        var response = service.create(dto);

        assertNotNull(response);
        assertEquals("123456789", response.phone());
        assertEquals("CO", response.countryCode());
        assertEquals(1L, response.passengerId());
    }

    @Test
    void testCreatePassengerNotFound() {
        CreatePassengerProfileDTO dto = new CreatePassengerProfileDTO("123456789", "CO", 99L);
        when(passengerRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.create(dto));
    }

    @Test
    void testGetSuccess() {
        when(profileRepo.findById(100L)).thenReturn(Optional.of(profile));

        var response = service.get(100L);

        assertEquals(100L, response.id());
        assertEquals("123456789", response.phone());
    }

    @Test
    void testGetNotFound() {
        when(profileRepo.findById(200L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.get(200L));
    }

    @Test
    void testUpdateSuccess() {
        UpdatePassengerProfileDTO dto = new UpdatePassengerProfileDTO(
                100L, "987654321", "MX", 1L
        );

        when(profileRepo.findById(100L)).thenReturn(Optional.of(profile));
        when(passengerRepo.findById(1L)).thenReturn(Optional.of(passenger));
        when(profileRepo.save(any(PassengerProfile.class))).thenReturn(profile);

        var response = service.update(100L, dto);

        assertNotNull(response);
        assertEquals("987654321", response.phone());
        assertEquals("MX", response.countryCode());
    }

    @Test
    void testUpdateProfileNotFound() {
        UpdatePassengerProfileDTO dto = new UpdatePassengerProfileDTO(200L, "987654321", "MX", 1L);

        when(profileRepo.findById(200L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.update(200L, dto));
    }

    @Test
    void testDeleteSuccess() {
        when(profileRepo.existsById(100L)).thenReturn(true);
        doNothing().when(profileRepo).deleteById(100L);

        service.delete(100L);

        verify(profileRepo, times(1)).deleteById(100L);
    }

    @Test
    void testDeleteNotFound() {
        when(profileRepo.existsById(200L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.delete(200L));
    }

    @Test
    void testListProfiles() {
        when(profileRepo.findAll()).thenReturn(List.of(profile));

        var responses = service.list();

        assertEquals(1, responses.size());
        assertEquals("123456789", responses.get(0).phone());
    }
}
