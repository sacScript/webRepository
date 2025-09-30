package com.example.demo.services.mappers;


import com.example.demo.api.dto.SeatInventoryDtos;
import com.example.demo.domain.entities.Flight;
import com.example.demo.domain.entities.SeatInventory;

public class SeatInventoryMapper {

    public static SeatInventory toEntity(SeatInventoryDtos.CreateSeatInventoryDTO req, Flight flight) {
        return SeatInventory.builder()
                .cabin(req.cabin())
                .totalSeats(req.totalSeats())
                .availableSeats(req.availableSeats())
                .flight(flight)
                .build();
    }

    public static SeatInventoryDtos.SeatInventoryResponseDTO toResponse(SeatInventory seatInventory) {
        return new SeatInventoryDtos.SeatInventoryResponseDTO(
                seatInventory.getId(),
                seatInventory.getCabin(),
                seatInventory.getTotalSeats(),
                seatInventory.getAvailableSeats(),
                seatInventory.getFlight() != null ? seatInventory.getFlight().getId() : null
        );
    }

    public static void patch(SeatInventory seatInventory, SeatInventoryDtos.UpdateSeatInventoryDTO req, Flight flight) {
        if (req.cabin() != null) seatInventory.setCabin(req.cabin());
        if (req.totalSeats() != null) seatInventory.setTotalSeats(req.totalSeats());
        if (req.availableSeats() != null) seatInventory.setAvailableSeats(req.availableSeats());
        if (req.flightId() != null && flight != null) seatInventory.setFlight(flight);
    }
}
