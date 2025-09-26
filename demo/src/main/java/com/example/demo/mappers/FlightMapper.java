package com.example.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.api.dto.FlightDtos;
import com.example.demo.domain.entities.Flight;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    FlightDtos toDTO(Flight flight);

    @Mapping(target = "id", ignore = true)
    Flight toEntity(FlightDtos flightDTO);
}
