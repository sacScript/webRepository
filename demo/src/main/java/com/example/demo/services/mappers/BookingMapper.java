package com.example.demo.services.mappers;

import java.time.OffsetDateTime;


import com.example.demo.api.dto.BookingDtos;
import com.example.demo.domain.entities.Booking;
import com.example.demo.domain.entities.BookingItem;
import com.example.demo.domain.entities.Flight;
import com.example.demo.domain.entities.Passenger;

public class BookingMapper {

   
    public static Booking toEntity(BookingDtos.CreateBookingDTO req, Passenger passenger, Flight flight) {
        var booking = Booking.builder()
                .createdAt(OffsetDateTime.now())
                .passenger(passenger)
                .build();

      
        var bookingItem = BookingItem.builder()
                .flight(flight)
                .booking(booking)
                .build();

        booking.addBookingItem(bookingItem);

        return booking;
    }

    
    public static BookingDtos.BookingResponseDTO toResponse(Booking booking) {
        
        var flightId = booking.getBookingItems().isEmpty()
                ? null
                : booking.getBookingItems().get(0).getFlight().getId();

        return new BookingDtos.BookingResponseDTO(
                booking.getId(),
                flightId,
                booking.getPassenger().getId()
        );
    }

    
    public static void patch(Booking booking, BookingDtos.UpdateBookingDTO req, Passenger passenger, Flight flight) {
        if (req.passengerId() != null && passenger != null) {
            booking.setPassenger(passenger);
        }

        if (req.flightId() != null && flight != null) {
        
            booking.getBookingItems().clear();
            var bookingItem = BookingItem.builder()
                    .flight(flight)
                    .booking(booking)
                    .build();
            booking.addBookingItem(bookingItem);
        }
    }
}
