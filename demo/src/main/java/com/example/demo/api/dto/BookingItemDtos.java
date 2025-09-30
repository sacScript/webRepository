package com.example.demo.api.dto;

import java.io.Serializable;

public class BookingItemDtos {

    public record CreateBookingItemDTO(Long bookingId, Long flightId) implements Serializable {
    }

    public record UpdateBookingItemDTO(Long id, Long bookingId, Long flightId) implements Serializable {
    }

    public record BookingItemResponseDTO(Long id, Long bookingId, Long flightId) implements Serializable {
    }
}

