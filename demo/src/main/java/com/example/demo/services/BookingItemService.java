package com.example.demo.services;

import java.util.List;

import com.example.demo.api.dto.BookingDtos.BookingItemCreateDTO;
import com.example.demo.api.dto.BookingDtos.BookingItemResponseDTO;



public interface BookingItemService {
    BookingItemResponseDTO addItem(Long bookingId,BookingItemCreateDTO req);
    BookingItemResponseDTO get(Long id);
    void removeItem(Long itemId);
    List<BookingItemResponseDTO> listByBooking(Long bookingId);
}