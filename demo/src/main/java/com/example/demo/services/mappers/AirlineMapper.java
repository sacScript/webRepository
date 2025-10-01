package com.example.demo.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.api.dto.AirlineDtos;
import com.example.demo.domain.entities.Airline;

@Mapper(componentModel = "spring")
public interface AirlineMapper {

    
    @Mapping(target = "id", ignore = true)
    Airline toEntity(AirlineDtos.AirlineCreateDto dto);

    AirlineDtos.AirlineResponse toResponse(Airline airline);

    void patch(@MappingTarget Airline airline, AirlineDtos.AirlineUpdateDto dto);
}
