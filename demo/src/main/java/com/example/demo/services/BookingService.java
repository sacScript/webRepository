package com.example.demo.services;

import java.util.List;

import com.example.demo.api.dto.BookingDtos;


public interface BookingService {
    BookingDtos.BookingResponseDTO create(BookingDtos.CreateBookingDTO req);
    BookingDtos.BookingResponseDTO get(Long id);
    BookingDtos.BookingResponseDTO update(Long id, BookingDtos.UpdateBookingDTO req);
    void delete(Long id);
    List<BookingDtos.BookingResponseDTO> list();
}
