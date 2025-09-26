package com.example.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.api.dto.AirportDtos;
import com.example.demo.domain.entities.Airport;

@Mapper(componentModel = "spring")
public interface AirportMapper  {
    AirportDtos toDTO(Airport airport);

    @Mapping(target = "id", ignore = true)
    Airport toEntity(AirportDtos airportDTO);
}