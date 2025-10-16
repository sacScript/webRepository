package com.example.demo.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.api.dto.BookingDtos.BookingResponseDTO;
import com.example.demo.api.dto.BookingDtos.CreateBookingDTO;
import com.example.demo.api.dto.BookingDtos.UpdateBookingDTO;


public interface BookingService {
    BookingResponseDTO create(Long passengerId, CreateBookingDTO req);
    BookingResponseDTO get(Long id);
    BookingResponseDTO update(Long id,UpdateBookingDTO req);
    void delete(Long id);
    Page<BookingResponseDTO> listByPassenger( String email,Pageable pageable);
}
