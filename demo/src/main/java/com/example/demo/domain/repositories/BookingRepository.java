package com.example.demo.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.entities.Booking;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

        Page<Booking> findByPassengerEmailIgnoreCaseOrderByCreatedAtDesc(
                        String email,
                        org.springframework.data.domain.Pageable pageable);

        @Query("SELECT b FROM Booking b " +
                        "LEFT JOIN FETCH b.bookingItems bi " +
                        "LEFT JOIN FETCH bi.flight " +
                        "LEFT JOIN FETCH b.passenger " +
                        "WHERE b.id = :id")
        Optional<Booking> findByIdWithDetails(@Param("id") Long id);

}