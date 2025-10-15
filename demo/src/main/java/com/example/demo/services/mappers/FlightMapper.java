package com.example.demo.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.api.dto.FlightDtos;
import com.example.demo.domain.entities.Flight;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    
    @Mapping(target = "id", ignore = true)
    Flight toEntity(FlightDtos.CreateFlightDTO dto);

    FlightDtos.FlightResponseDTO toResponse(Flight Flight);

    void patch(@MappingTarget Flight Flight, FlightDtos.UpdateFlightDTO dto);
}
