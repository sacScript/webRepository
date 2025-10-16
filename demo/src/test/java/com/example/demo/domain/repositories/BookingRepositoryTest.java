package com.example.demo.domain.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.domain.entities.Booking;
import com.example.demo.domain.entities.BookingItem;
import com.example.demo.domain.entities.Flight;
import com.example.demo.domain.entities.Passenger;

class BookingRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private BookingRepository bookingRepo;
    @Autowired
    private PassengerRepository passengerRepo;
    @Autowired
    private BookingItemRepository bookingItemRepo;
    @Autowired
    private FlightRepository flightRepo;

    private Passenger passenger;

    @BeforeEach
    void setUp() {
        passenger = passengerRepo.save( Passenger.builder().fullName("Juan").email("juan.perez@example.com").build());
    }

    @Test
    void testFindByPassengerEmailIgnoreCaseOrderByCreatedAtDesc() {
        // Given
        var booking1 = Booking.builder().passenger(passenger).build();
        bookingRepo.save(booking1);
        var booking2 = Booking.builder().passenger(passenger).build();
        bookingRepo.save(booking2);
        Pageable pageable = PageRequest.of(0, 10);

        // When
        var resultPage = bookingRepo.findByPassengerEmailIgnoreCaseOrderByCreatedAtDesc(passenger.getEmail(), pageable);

        // Then
        assertThat(resultPage).hasSize(2);
        var bookings = resultPage.getContent();
        assertThat(bookings.get(0).getId()).isEqualTo(booking2.getId());
        assertThat(bookings.get(1).getId()).isEqualTo(booking1.getId());
    }

    @Test
    void testFindByIdWithDetails() {
        // Given
        var booking = Booking.builder().passenger(passenger).build();
        bookingRepo.save(booking);
        var flight = flightRepo.save( Flight.builder().number("LA777").build());
        var item = BookingItem.builder().booking(booking).flight(flight).build();
        bookingItemRepo.save(item);

        // When
        Booking foundBooking = bookingRepo.findByIdWithDetails(booking.getId()).orElse(null);

        // Then
        assertThat(foundBooking).isNotNull();
        assertThat(foundBooking.getBookingItems()).hasSize(1);
        assertThat(foundBooking.getBookingItems().iterator().next().getId()).isEqualTo(item.getId());
        assertThat(foundBooking.getBookingItems().iterator().next().getFlight().getNumber()).isEqualTo("LA777");
    }
}