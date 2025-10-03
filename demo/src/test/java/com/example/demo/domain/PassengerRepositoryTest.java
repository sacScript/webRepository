package com.example.demo.domain;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.domain.entities.Passenger;
import com.example.demo.domain.entities.PassengerProfile;
import com.example.demo.domain.repositories.PassengerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PassengerRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private TestEntityManager entityManager;

    private Passenger passenger;

    @BeforeEach
    void setUp() {
        passenger = new Passenger(null, "Test User", "test.user@example.com", null, null);
        PassengerProfile profile = new PassengerProfile(null, "12345678A", "Calle Test", passenger);
        passenger.setProfile(profile);
        passengerRepository.save(passenger);

        // Limpiar el contexto de persistencia para forzar la carga de la base de datos
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void testFindByEmailIgnoreCaseWithProfile() {
        // Act
        Optional<Passenger> foundPassenger = passengerRepository.findByEmailIgnoreCaseWithProfile("TEST.USER@example.com");

        // Assert
        assertThat(foundPassenger).isPresent();
        Passenger result = foundPassenger.get();
        assertThat(result.getFullName()).isEqualTo("Test User");
        // Verificar que el perfil se cargó con el JOIN FETCH
        assertThat(result.getProfile()).isNotNull();
        assertThat(result.getProfile().getId()).isEqualTo("12345678A");
    }
}