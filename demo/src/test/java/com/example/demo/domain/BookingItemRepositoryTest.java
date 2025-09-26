package com.example.demo.domain;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.domain.entities.*;
import com.example.demo.domain.repositories.BookingItemRepository;
import com.example.demo.domain.repositories.BookingRepository;
import com.example.demo.domain.repositories.FlightRepository;
import com.example.demo.domain.repositories.PassengerRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookingItemRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private BookingItemRepository bookingItemRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired

    @Test
    void testCalculateTotalByBookingId() {
        // Arrange
        Passenger passenger = passengerRepository.save(new Passenger(null, "Test", "test@example.com", null, null));
        Booking booking = bookingRepository.save(new Booking(null, null, passenger, null));

        BookingItem item1 = new BookingItem();
        BookingItem item2 = new BookingItem();
        bookingItemRepository.saveAll(List.of(item1, item2));

        // Act
        BigDecimal total = bookingItemRepository.calculateTotalByBookingId(booking.getId());

        // Assert
        assertThat(total).isEqualByComparingTo(new BigDecimal("351.00"));
    }
}