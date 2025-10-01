package com.example.demo.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.api.dto.BookingItemDtos;
import com.example.demo.domain.entities.BookingItem;

@Mapper(componentModel = "spring")
public interface BookingItemMapper {

    
    @Mapping(target = "id", ignore = true)
    BookingItem toEntity(BookingItemDtos.CreateBookingItemDTO dto);

    BookingItemDtos.BookingItemResponseDTO toResponse(BookingItem BookingItem);

    void patch(@MappingTarget BookingItem BookingItem, BookingItemDtos.UpdateBookingItemDTO dto);
}
