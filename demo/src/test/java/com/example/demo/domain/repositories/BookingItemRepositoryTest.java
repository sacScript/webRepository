package com.example.demo.domain.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.domain.entities.Booking;
import com.example.demo.domain.entities.BookingItem;
import com.example.demo.domain.entities.Passenger;
import com.example.demo.domain.entities.PassengerProfile;

class BookingItemRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private BookingItemRepository bookingItemRepo;
    @Autowired
    private BookingRepository bookingRepo;
    @Autowired
    private PassengerRepository passengerRepo;
    @Autowired

    @Test
    void testCalculateTotalByBookingId() {
        // Given
        var passengerProfile = PassengerProfile.builder().phone("123-456-7890").countryCode("US").build();
        var passenger = Passenger.builder().fullName("Test").email("test@example.com").profile(passengerProfile).build();
        var booking = Booking.builder().passenger(passenger).build();
        passengerRepo.save(passenger);
        bookingRepo.save(booking);

        var item1 = BookingItem.builder().booking(booking).price(new BigDecimal("150.00")).build();
        var item2 = BookingItem.builder().booking(booking).price(new BigDecimal("200.00")).build();
        bookingItemRepo.saveAll(List.of(item1, item2));

        // When
        var total = bookingItemRepo.calculateTotalByBookingId(booking.getId());

        // Then
        assertThat(total).isEqualByComparingTo(new BigDecimal("350.00"));
    }
}