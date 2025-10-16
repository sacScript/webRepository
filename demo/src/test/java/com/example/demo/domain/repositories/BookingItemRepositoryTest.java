package com.example.demo.domain.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.domain.entities.Booking;
import com.example.demo.domain.entities.BookingItem;
import com.example.demo.domain.entities.Passenger;
import com.example.demo.domain.repositories.BookingItemRepository;
import com.example.demo.domain.repositories.BookingRepository;
import com.example.demo.domain.repositories.PassengerRepository;

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
        Passenger passenger = passengerRepository.save(Passenger.builder().fullName("Test").email("test@example.com").build());
        Booking booking = bookingRepository.save(Booking.builder().passenger(passenger).build());

        BookingItem item1 = BookingItem.builder().booking(booking).price(new BigDecimal("100.00")).build();
        BookingItem item2 = BookingItem.builder().booking(booking).price(new BigDecimal("251.00")).build();
        bookingItemRepository.saveAll(List.of(item1, item2));

        // Act
        BigDecimal total = bookingItemRepository.calculateTotalByBookingId(booking.getId());

        // Assert
        assertThat(total).isEqualByComparingTo(new BigDecimal("351.00"));
    }
}