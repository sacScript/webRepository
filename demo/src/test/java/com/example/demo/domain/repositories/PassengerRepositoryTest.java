package com.example.demo.domain.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.domain.entities.Passenger;
import com.example.demo.domain.entities.PassengerProfile;

class PassengerRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        var passenger = Passenger.builder()
                .fullName("Test User")
                .email("test.user@example.com")
                .build();
        PassengerProfile profile = PassengerProfile.builder()
                .phone("123-456-7890")
                .countryCode("US")
                .build();
        passenger.setProfile(profile);
        passengerRepository.save(passenger);

        // Limpiar el contexto de persistencia para forzar la carga de la base de datos
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void testFindByEmailIgnoreCaseWithProfile() {
        // Act
        Optional<Passenger> foundPassenger = passengerRepository
                .findByEmailIgnoreCaseWithProfile("TEST.USER@example.com");

        // Assert
        assertThat(foundPassenger).isPresent();
        Passenger result = foundPassenger.get();
        assertThat(result.getFullName()).isEqualTo("Test User");
        // Verificar que el perfil se cargó con el JOIN FETCH
        assertThat(result.getProfile()).isNotNull();
        assertThat(result.getProfile().getId()).isEqualTo("12345678A");
    }
}