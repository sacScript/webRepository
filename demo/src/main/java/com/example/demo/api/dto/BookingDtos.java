package com.example.demo.api.dto;

import java.io.Serializable;

public class BookingDtos {
    public record CreateBookingDTO(Long flightId, Long passengerId) implements Serializable {
    }

    public record UpdateBookingDTO(Long id, Long flightId, Long passengerId) implements Serializable {
    }

    public record BookingResponseDTO(Long id, Long flightId, Long passengerId) implements Serializable {
    }
}
