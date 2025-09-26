package com.example.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.api.dto.BookingItemDtos;
import com.example.demo.domain.entities.BookingItem;

@Mapper(componentModel = "spring")
public interface BookingItemMapper {
    BookingItemDtos toDTO(BookingItem bookingItem);

    @Mapping(target = "id", ignore = true)
    BookingItem toEntity(BookingItemDtos bookingItemDTO);
}

