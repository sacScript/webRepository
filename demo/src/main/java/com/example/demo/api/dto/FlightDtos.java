package com.example.demo.api.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.example.demo.domain.entities.Airport;
import com.example.demo.domain.entities.BookingItem;
import com.example.demo.domain.entities.SeatInventory;
import com.example.demo.domain.entities.Tag;

public class FlightDtos {
        public record CreateFlightDTO(Long number, Long airlineId, Airport origin, Airport destination,
                        String departureTime, String arrivalTime)
                        implements Serializable {
        }

        public record UpdateFlightDTO(Long id, Long number, Long airlineId, Airport origin, Airport destination,
                        String departureTime, String arrivalTime)
                        implements Serializable {
        }

        public record FlightResponseDTO(Long id, Long number, Long airlineId, String airlineName, Airport origin, Airport destination,
                        String departureTime,
                        String arrivalTime, Set<Tag> tags, Set<BookingItem> bookingItems,
                        List<SeatInventory> seatInventories) implements Serializable {
        }
}
