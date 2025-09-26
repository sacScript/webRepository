package com.example.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.domain.entities.Booking;
import com.example.demo.domain.entities.BookingItem;
import com.example.demo.domain.entities.Passenger;
import com.example.demo.domain.repositories.BookingItemRepository;
import com.example.demo.domain.repositories.BookingRepository;
import com.example.demo.domain.repositories.PassengerRepository;

@DataJpaTest
class BookingRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private BookingItemRepository bookingItemRepository;

    private Passenger passenger;

    @BeforeEach
    void setUp() {
        passenger = passengerRepository.save(new Passenger(null, "Juan", "juan.perez@example.com", null, null));
    }

    @Test
    void testFindByPassengerEmailIgnoreCaseOrderByCreatedAtDesc() {
        // Arrange
        // Crear reservas con diferentes fechas
        Booking booking1 = bookingRepository.save(new Booking());
        Booking booking2 = bookingRepository.save(new Booking());

        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Booking> resultPage = bookingRepository.findByPassengerEmailIgnoreCaseOrderByCreatedAtDesc(passenger.getEmail(), pageable);

        // Assert
        assertThat(resultPage).hasSize(2);
        List<Booking> bookings = resultPage.getContent();
        // Verificar que el orden es descendente
        assertThat(bookings.get(0).getId()).isEqualTo(booking2.getId());
        assertThat(bookings.get(1).getId()).isEqualTo(booking1.getId());
    }

    @Test
    void testFindByIdWithDetails() {
        // Arrange
        Booking booking = bookingRepository.save(new Booking(null, null, passenger, null));
        BookingItem item = bookingItemRepository.save(new BookingItem());

        // Act
        Booking foundBooking = bookingRepository.findByIdWithDetails(booking.getId()).orElse(null);

        // Assert
        assertThat(foundBooking).isNotNull();
        assertThat(foundBooking.getBookingItems()).hasSize(1);
        // Verificar que el item y el vuelo se cargaron correctamente
        assertThat(foundBooking.getBookingItems().iterator().next().getId()).isEqualTo(item.getId());
        assertThat(foundBooking.getBookingItems().iterator().next().getFlight().getNumber()).isEqualTo("LA777");
    }
}