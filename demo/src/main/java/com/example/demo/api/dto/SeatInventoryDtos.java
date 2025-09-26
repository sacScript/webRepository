package com.example.demo.api.dto;

import java.io.Serializable;

import com.example.demo.domain.enums.Cabin;

public class SeatInventoryDtos {
    public record CreateSeatInventoryDTO(Cabin cabin, Integer totalSeats, Integer availableSeats, Long flightId)
            implements Serializable {
    }

    public record UpdateSeatInventoryDTO(Long id, Cabin cabin, Integer totalSeats, Integer availableSeats,
            Long flightId) implements Serializable {
    }

    public record SeatInventoryResponseDTO(Long id, Cabin cabin, Integer totalSeats, Integer availableSeats,
            Long flightId) implements Serializable {
    }
}
