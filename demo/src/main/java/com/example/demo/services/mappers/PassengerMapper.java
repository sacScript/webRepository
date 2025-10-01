package com.example.demo.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.api.dto.PassengerDtos;
import com.example.demo.domain.entities.Passenger;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    
    @Mapping(target = "id", ignore = true)
    Passenger toEntity(PassengerDtos.CreatePassengerDTO dto);

    PassengerDtos.PassengerResponseDTO toResponse(Passenger Passenger);

    void patch(@MappingTarget Passenger Passenger, PassengerDtos.UpdatePassengerDTO dto);
}

