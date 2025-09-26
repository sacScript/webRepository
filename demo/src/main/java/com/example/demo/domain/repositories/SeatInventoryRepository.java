package com.example.demo.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.entities.SeatInventory;
import com.example.demo.domain.enums.Cabin;

import java.util.Optional;

public interface SeatInventoryRepository extends JpaRepository<SeatInventory, Long> {

    Optional<SeatInventory> findByFlightIdAndCabin(Long flightId, Cabin cabin);

    @Query("""
           SELECT CASE WHEN COUNT(si) > 0 THEN true ELSE false END
           FROM SeatInventory si
           WHERE si.flight.id = :flightId
             AND si.cabin = :cabin
             AND si.availableSeats >= :min
           """)
    boolean hasAvailableSeats(@Param("flightId") Long flightId,
                              @Param("cabin") Cabin cabin,
                              @Param("min") int min);
}
