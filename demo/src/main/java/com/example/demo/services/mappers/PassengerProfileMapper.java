package com.example.demo.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.api.dto.PassengerProfileDtos;
import com.example.demo.domain.entities.PassengerProfile;

@Mapper(componentModel = "spring")
public interface PassengerProfileMapper {

    
    @Mapping(target = "id", ignore = true)
    PassengerProfile toEntity(PassengerProfileDtos.CreatePassengerProfileDTO dto);

    PassengerProfileDtos.PassengerProfileResponseDTO toResponse(PassengerProfile PassengerProfile);

    void patch(@MappingTarget PassengerProfile PassengerProfile, PassengerProfileDtos.UpdatePassengerProfileDTO dto);
}
