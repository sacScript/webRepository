package com.example.demo.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.example.demo.domain.enums.Cabin;

public class BookingDtos {
    public record CreateBookingDTO(Long passengerId) implements Serializable {
    }

    public record UpdateBookingDTO(Long id, Long passengerId) implements Serializable {
    }

    public record BookingResponseDTO(Long id, String passengerFullName, String passengerEmail, OffsetDateTime createdAt,
            List<BookingItemResponseDTO> items)
            implements Serializable {
    }

    public record BookingItemResponseDTO(Long id, Long flightId, Long bookingId, Cabin cabin, BigDecimal price,
            Integer segmentOrder,
            String flightNumber) implements Serializable {
    }

    public record BookingItemCreateDTO(Long flightId, Long bookingId, Cabin cabin, BigDecimal price,
            Integer segmentOrder) implements Serializable {
    }

}
