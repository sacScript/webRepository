package com.example.demo.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.api.dto.AirportDtos;
import com.example.demo.domain.entities.Airport;

@Mapper(componentModel = "spring")
public interface AirportMapper {

    
    @Mapping(target = "id", ignore = true)
    Airport toEntity(AirportDtos.CreateAirportDTO dto);

    AirportDtos.AirportResponseDTO toResponse(Airport airport);

    void patch(@MappingTarget Airport airport, AirportDtos.UpdateAirportDTO dto);
}
