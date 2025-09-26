package com.example.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.api.dto.SeatInventoryDtos;
import com.example.demo.domain.entities.SeatInventory;

@Mapper(componentModel = "spring")
public interface SeatInventoryMapper {
    SeatInventoryDtos toDTO(SeatInventory seatInventory);

    @Mapping(target = "id", ignore = true)
    SeatInventory toEntity(SeatInventoryDtos seatInventoryDTO);
}