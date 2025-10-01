package com.example.demo.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.api.dto.BookingDtos;
import com.example.demo.domain.entities.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper{

    @Mapping(target = "id", ignore = true)
    Booking toEntity(BookingDtos.CreateBookingDTO dto);

    BookingDtos.BookingResponseDTO toResponse(Booking Booking);

    void patch(@MappingTarget Booking Booking, BookingDtos.UpdateBookingDTO dto);
}