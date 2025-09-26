package com.example.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.api.dto.AirlineDtos;
import com.example.demo.domain.entities.Airline;

@Mapper(componentModel = "spring")
public interface AirlineMapper {
    AirlineDtos toDTO(Airline airline);

    @Mapping(target = "id", ignore = true)
    Airline toEntity(AirlineDtos airlineDTO);
}
