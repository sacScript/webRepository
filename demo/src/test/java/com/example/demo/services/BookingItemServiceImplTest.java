package com.example.demo.services;

import com.example.demo.api.dto.BookingItemDtos;
import com.example.demo.domain.entities.Booking;
import com.example.demo.domain.entities.BookingItem;
import com.example.demo.domain.entities.Flight;
import com.example.demo.domain.repositories.BookingItemRepository;
import com.example.demo.domain.repositories.BookingRepository;
import com.example.demo.domain.repositories.FlightRepository;
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
class BookingItemServiceImplTest {

    @Mock
    BookingItemRepository bookingItemRepo;

    @Mock
    BookingRepository bookingRepo;

    @Mock
    FlightRepository flightRepo;

    @InjectMocks
    BookingItemServiceImpl service;

    @Test
    void shouldCreateBookingItem() {
        var booking = new Booking();
        booking.setId(10L);
        var flight = new Flight();
        flight.setId(20L);

        var req = new BookingItemDtos.CreateBookingItemDTO(10L, 20L);

        when(bookingRepo.findById(10L)).thenReturn(Optional.of(booking));
        when(flightRepo.findById(20L)).thenReturn(Optional.of(flight));
        when(bookingItemRepo.save(any())).thenAnswer(inv -> {
            BookingItem bi = inv.getArgument(0);
            bi.setId(1L);
            return bi;
        });

        var res = service.create(req);

        assertThat(res.id()).isEqualTo(1L);
        assertThat(res.bookingId()).isEqualTo(10L);
        assertThat(res.flightId()).isEqualTo(20L);
        verify(bookingItemRepo).save(any(BookingItem.class));
    }

    @Test
    void shouldThrowWhenBookingNotFoundOnCreate() {
        var req = new BookingItemDtos.CreateBookingItemDTO(99L, 20L);
        when(bookingRepo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Booking 99 not found");
    }

    @Test
    void shouldGetBookingItem() {
        var booking = new Booking();
        booking.setId(10L);
        var flight = new Flight();
        flight.setId(20L);
        var bookingItem = new BookingItem();
        bookingItem.setId(1L);
        bookingItem.setBooking(booking);
        bookingItem.setFlight(flight);

        when(bookingItemRepo.findById(1L)).thenReturn(Optional.of(bookingItem));

        var res = service.get(1L);

        assertThat(res.id()).isEqualTo(1L);
        assertThat(res.bookingId()).isEqualTo(10L);
        assertThat(res.flightId()).isEqualTo(20L);
    }

    @Test
    void shouldThrowWhenBookingItemNotFoundOnGet() {
        when(bookingItemRepo.findById(77L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.get(77L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("BookingItem 77 not found");
    }

    @Test
    void shouldListAllBookingItems() {
        var booking = new Booking();
        booking.setId(10L);
        var flight = new Flight();
        flight.setId(20L);
        var bookingItem = new BookingItem();
        bookingItem.setId(1L);
        bookingItem.setBooking(booking);
        bookingItem.setFlight(flight);

        when(bookingItemRepo.findAll()).thenReturn(List.of(bookingItem));

        var list = service.list();

        assertThat(list).hasSize(1);
        assertThat(list.get(0).id()).isEqualTo(1L);
        assertThat(list.get(0).bookingId()).isEqualTo(10L);
        assertThat(list.get(0).flightId()).isEqualTo(20L);
    }

    @Test
    void shouldUpdateBookingItem() {
        var oldBooking = new Booking();
        oldBooking.setId(10L);
        var oldFlight = new Flight();
        oldFlight.setId(20L);
        var bookingItem = new BookingItem();
        bookingItem.setId(1L);
        bookingItem.setBooking(oldBooking);
        bookingItem.setFlight(oldFlight);

        var newBooking = new Booking();
        newBooking.setId(30L);
        var newFlight = new Flight();
        newFlight.setId(40L);

        when(bookingItemRepo.findById(1L)).thenReturn(Optional.of(bookingItem));
        when(bookingRepo.findById(30L)).thenReturn(Optional.of(newBooking));
        when(flightRepo.findById(40L)).thenReturn(Optional.of(newFlight));
        when(bookingItemRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var updateDto = new BookingItemDtos.UpdateBookingItemDTO(1L, 30L, 40L);
        var res = service.update(1L, updateDto);

        assertThat(res.bookingId()).isEqualTo(30L);
        assertThat(res.flightId()).isEqualTo(40L);
    }

    @Test
    void shouldDeleteBookingItem() {
        service.delete(5L);
        verify(bookingItemRepo).deleteById(5L);
    }
}
