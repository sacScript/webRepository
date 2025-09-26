package com.example.demo.api.dto;

import java.io.Serializable;

public class FlightDtos {
    public record CreateFlightDTO(String origin, String destination, String departureTime, String arrivalTime)
            implements Serializable {
    }

    public record UpdateFlightDTO(Long id, String origin, String destination, String departureTime, String arrivalTime)
            implements Serializable {
    }

    public record FlightResponseDTO(Long id, String origin, String destination, String departureTime,
            String arrivalTime) implements Serializable {
    }
}
