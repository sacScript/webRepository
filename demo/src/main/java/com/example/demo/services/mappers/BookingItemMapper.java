package com.example.demo.services.mappers;



import com.example.demo.api.dto.BookingItemDtos;
import com.example.demo.domain.entities.Booking;
import com.example.demo.domain.entities.BookingItem;
import com.example.demo.domain.entities.Flight;

public class BookingItemMapper {

    public static BookingItem toEntity(BookingItemDtos.CreateBookingItemDTO req, Booking booking, Flight flight) {
        return BookingItem.builder()
                .booking(booking)
                .flight(flight)
                .build();
    }

    public static BookingItemDtos.BookingItemResponseDTO toResponse(BookingItem bookingItem) {
        return new BookingItemDtos.BookingItemResponseDTO(
                bookingItem.getId(),
                bookingItem.getBooking().getId(),
                bookingItem.getFlight().getId()
        );
    }

    public static void patch(BookingItem bookingItem, BookingItemDtos.UpdateBookingItemDTO req, Booking booking, Flight flight) {
        if (req.bookingId() != null && booking != null) bookingItem.setBooking(booking);
        if (req.flightId() != null && flight != null) bookingItem.setFlight(flight);
    }
}

