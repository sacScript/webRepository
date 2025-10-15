package com.example.demo.services;


import com.example.demo.api.dto.BookingDtos;
import com.example.demo.domain.entities.Booking;
import com.example.demo.domain.entities.BookingItem;
import com.example.demo.domain.entities.Flight;
import com.example.demo.domain.entities.Passenger;
import com.example.demo.domain.repositories.BookingRepository;
import com.example.demo.domain.repositories.FlightRepository;
import com.example.demo.domain.repositories.PassengerRepository;
import com.example.demo.exceptions.NotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    BookingRepository bookingRepo;

    @Mock
    PassengerRepository passengerRepo;

    @Mock
    FlightRepository flightRepo;

    @InjectMocks
    BookingServiceImpl service;

    @Test
    void shouldCreateBooking() {
        var passenger = new Passenger();
        passenger.setId(10L);
        var flight = new Flight();
        flight.setId(20L);

        var req = new BookingDtos.CreateBookingDTO(20L, 10L);

        when(passengerRepo.findById(10L)).thenReturn(Optional.of(passenger));
        when(flightRepo.findById(20L)).thenReturn(Optional.of(flight));
        when(bookingRepo.save(any())).thenAnswer(inv -> {
            Booking b = inv.getArgument(0);
            b.setId(1L);
            return b;
        });

        var res = service.create(req);

        assertThat(res.id()).isEqualTo(1L);
        assertThat(res.passengerId()).isEqualTo(10L);
        assertThat(res.flightId()).isEqualTo(20L);
        verify(bookingRepo).save(any(Booking.class));
    }

    @Test
    void shouldThrowWhenPassengerNotFoundOnCreate() {
        var req = new BookingDtos.CreateBookingDTO(20L, 99L);
        when(passengerRepo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Passenger 99 not found");
    }

    @Test
    void shouldThrowWhenFlightNotFoundOnCreate() {
        var passenger = new Passenger();
        passenger.setId(10L);
        var req = new BookingDtos.CreateBookingDTO(77L, 10L);

        when(passengerRepo.findById(10L)).thenReturn(Optional.of(passenger));
        when(flightRepo.findById(77L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Flight 77 not found");
    }

    @Test
    void shouldGetBooking() {
        var passenger = new Passenger();
        passenger.setId(10L);
        var flight = new Flight();
        flight.setId(20L);

        var booking = new Booking();
        booking.setId(1L);
        booking.setPassenger(passenger);

        var bookingItem = new BookingItem();
        bookingItem.setId(100L);
        bookingItem.setBooking(booking);
        bookingItem.setFlight(flight);

        booking.setBookingItems(List.of(bookingItem));

        when(bookingRepo.findById(1L)).thenReturn(Optional.of(booking));

        var res = service.get(1L);

        assertThat(res.id()).isEqualTo(1L);
        assertThat(res.passengerId()).isEqualTo(10L);
        assertThat(res.flightId()).isEqualTo(20L);
    }

    @Test
    void shouldThrowWhenBookingNotFoundOnGet() {
        when(bookingRepo.findById(77L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.get(77L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Booking 77 not found");
    }

    @Test
    void shouldListBookings() {
        var passenger = new Passenger();
        passenger.setId(10L);
        var flight = new Flight();
        flight.setId(20L);

        var booking = new Booking();
        booking.setId(1L);
        booking.setPassenger(passenger);

        var bookingItem = new BookingItem();
        bookingItem.setId(100L);
        bookingItem.setBooking(booking);
        bookingItem.setFlight(flight);

        booking.setBookingItems(List.of(bookingItem));

        when(bookingRepo.findAll()).thenReturn(List.of(booking));

        var list = service.list();

        assertThat(list).hasSize(1);
        assertThat(list.get(0).id()).isEqualTo(1L);
        assertThat(list.get(0).passengerId()).isEqualTo(10L);
        assertThat(list.get(0).flightId()).isEqualTo(20L);
    }

    @Test
    void shouldUpdateBooking() {
        var oldPassenger = new Passenger();
        oldPassenger.setId(10L);
        var oldFlight = new Flight();
        oldFlight.setId(20L);

        var booking = new Booking();
        booking.setId(1L);
        booking.setPassenger(oldPassenger);

        var bookingItem = new BookingItem();
        bookingItem.setId(100L);
        bookingItem.setBooking(booking);
        bookingItem.setFlight(oldFlight);

        booking.setBookingItems(List.of(bookingItem));

        var newPassenger = new Passenger();
        newPassenger.setId(30L);
        var newFlight = new Flight();
        newFlight.setId(40L);

        when(bookingRepo.findById(1L)).thenReturn(Optional.of(booking));
        when(passengerRepo.findById(30L)).thenReturn(Optional.of(newPassenger));
        when(flightRepo.findById(40L)).thenReturn(Optional.of(newFlight));
        when(bookingRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var req = new BookingDtos.UpdateBookingDTO(1L, 40L, 30L);
        var res = service.update(1L, req);

        assertThat(res.passengerId()).isEqualTo(30L);
        assertThat(res.flightId()).isEqualTo(40L);
    }

    @Test
    void shouldDeleteBooking() {
        service.delete(5L);
        verify(bookingRepo).deleteById(5L);
    }
}