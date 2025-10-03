package com.example.demo.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.entities.Flight;

import java.time.OffsetDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {


    List<Flight> findByAirline_Name(String airlineName);
    Page<Flight> findByAirline_Name(String airlineName, Pageable pageable);

    Page<Flight> findByOrigin_CodeAndDestination_CodeAndDepartureTimeBetween(
            String origin,
            String destination,
            OffsetDateTime from,
            OffsetDateTime to,
            Pageable pageable);

    @Query("""
        SELECT DISTINCT f FROM Flight f
        JOIN FETCH f.airline
        JOIN FETCH f.origin
        JOIN FETCH f.destination
        LEFT JOIN FETCH f.tags
        WHERE (:o IS NULL OR f.origin.code = :o)
          AND (:d IS NULL OR f.destination.code = :d)
          AND f.departureTime BETWEEN :from AND :to
    """)
    List<Flight> searchFlightsWithDetails(
            @Param("o") String origin,
            @Param("d") String destination,
            @Param("from") OffsetDateTime from,
            @Param("to") OffsetDateTime to);

    @Query(value = """
        SELECT f.* FROM flights f
        JOIN flight_tags ft ON f.id = ft.flight_id
        JOIN tags t ON ft.tag_id = t.id
        WHERE t.name IN (:tags)
        GROUP BY f.id
        HAVING COUNT(DISTINCT t.name) = :required
    """, nativeQuery = true)
    List<Flight> findFlightsWithAllTags(
            @Param("tags") List<String> tags,
            @Param("required") long required);
}