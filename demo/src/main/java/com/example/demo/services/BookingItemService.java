package com.example.demo.services;

import java.util.List;

import com.example.demo.api.dto.BookingItemDtos.BookingItemResponseDTO;
import com.example.demo.api.dto.BookingItemDtos.CreateBookingItemDTO;
import com.example.demo.api.dto.BookingItemDtos.UpdateBookingItemDTO;

public interface BookingItemService {
    BookingItemResponseDTO create(CreateBookingItemDTO req);
    BookingItemResponseDTO get(Long id);
    BookingItemResponseDTO update(Long id,  UpdateBookingItemDTO req);
    void delete(Long id);
    List<BookingItemResponseDTO> list();
    

    
}