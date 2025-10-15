package com.example.demo.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.api.dto.BookingDtos;
import com.example.demo.domain.entities.Booking;
import com.example.demo.domain.entities.BookingItem;

@Mapper(componentModel = "spring")
public interface BookingMapper{

    @Mapping(target = "id", ignore = true)
    Booking toEntity(BookingDtos.CreateBookingDTO dto);

    BookingDtos.BookingResponseDTO toResponse(Booking Booking);

    void patch(@MappingTarget Booking Booking, BookingDtos.UpdateBookingDTO dto);

    @Mapping(target = "id", ignore = true)
    BookingItem toEntity(BookingDtos.BookingItemCreateDTO dto);
    
    BookingDtos.BookingItemResponseDTO toResponse(BookingItem item);
}