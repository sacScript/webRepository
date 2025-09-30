package com.example.demo.services.mappers;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.demo.api.dto.FlightDtos;
import com.example.demo.domain.entities.Airline;
import com.example.demo.domain.entities.Airport;
import com.example.demo.domain.entities.Flight;

public class FlightMapper {

    public static Flight toEntity(FlightDtos.CreateFlightDTO req, Airport origin, Airport destination, Airline airline) {
        return Flight.builder()
                .number(UUID.randomUUID().toString())
                .departureTime(OffsetDateTime.parse(req.departureTime()))
                .arrivalTime(OffsetDateTime.parse(req.arrivalTime()))
                .origin(origin)
                .destination(destination)
                .airline(airline)
                .build();
    }

    public static FlightDtos.FlightResponseDTO toResponse(Flight flight) {
        return new FlightDtos.FlightResponseDTO(
                flight.getId(),
                flight.getOrigin().getCode(),
                flight.getDestination().getCode(),
                flight.getDepartureTime().toString(),
                flight.getArrivalTime().toString()
        );
    }

    public static void patch(Flight flight, FlightDtos.UpdateFlightDTO req, Airport origin, Airport destination) {
        if (req.origin() != null && origin != null) flight.setOrigin(origin);
        if (req.destination() != null && destination != null) flight.setDestination(destination);
        if (req.departureTime() != null) flight.setDepartureTime(OffsetDateTime.parse(req.departureTime()));
        if (req.arrivalTime() != null) flight.setArrivalTime(OffsetDateTime.parse(req.arrivalTime()));
    }
}
