package com.example.demo.domain;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.domain.entities.Passenger;
import com.example.demo.domain.entities.PassengerProfile;
import com.example.demo.domain.repositories.PassengerProfileRepository;
import com.example.demo.domain.repositories.PassengerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PassengerProfileRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private PassengerProfileRepository passengerProfileRepository;
    @Autowired
    private PassengerRepository passengerRepository;

    private Passenger passenger;

    @BeforeEach
    void setUp() {
        passenger = passengerRepository.save(new Passenger(null, "Test Profile", "profile@example.com", null, null));
    }

    @Test
    void testSaveAndFindById() {
        // Arrange
        PassengerProfile profile = new PassengerProfile(null, "12345678B", "Calle Falsa 123", passenger);
        
        // Act
        PassengerProfile savedProfile = passengerProfileRepository.save(profile);
        PassengerProfile foundProfile = passengerProfileRepository.findById(savedProfile.getId()).orElse(null);

        // Assert
        assertThat(foundProfile).isNotNull();
        assertThat(foundProfile.getId()).isEqualTo("12345678B");
        assertThat(foundProfile.getClass()).isEqualTo("Calle Falsa 123");
        assertThat(foundProfile.getPassenger().getEmail()).isEqualTo("profile@example.com");
    }
}