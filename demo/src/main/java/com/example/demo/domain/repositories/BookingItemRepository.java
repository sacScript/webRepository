package com.example.demo.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.entities.BookingItem;
import com.example.demo.domain.enums.Cabin;

import java.math.BigDecimal;
import java.util.List;


public interface BookingItemRepository extends JpaRepository<BookingItem, Long> {

    List<BookingItem> findByBooking_IdOrderBySegmentOrderAsc(Long bookingId);

    @Query("SELECT COALESCE(SUM(bi.price), 0) " +
            "FROM BookingItem bi " +
            "WHERE bi.booking.id = :bookingId")
    BigDecimal calculateTotalByBookingId(@Param("bookingId") Long bookingId);

    @Query("SELECT COUNT(bi) " +
            "FROM BookingItem bi " +
            "WHERE bi.flight.id = :flightId " +
            "AND bi.cabin = :cabin")
    long countSeatsByFlightAndCabin(@Param("flightId") Long flightId,
                                    @Param("cabin") Cabin cabin);

}