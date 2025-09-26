package com.example.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.api.dto.BookingDtos;
import com.example.demo.domain.entities.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDtos toDTO(Booking booking);

    @Mapping(target = "id", ignore = true)
    Booking toEntity(BookingDtos bookingDTO);
}