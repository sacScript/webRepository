package com.example.demo.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.api.dto.SeatInventoryDtos;
import com.example.demo.domain.entities.SeatInventory;

@Mapper(componentModel = "spring")
public interface SeatInventoryMapper {

    
    @Mapping(target = "id", ignore = true)
    SeatInventory toEntity(SeatInventoryDtos.CreateSeatInventoryDTO dto);

    SeatInventoryDtos.SeatInventoryResponseDTO toResponse(SeatInventory SeatInventory);

    void patch(@MappingTarget SeatInventory SeatInventory, SeatInventoryDtos.UpdateSeatInventoryDTO dto);
}
