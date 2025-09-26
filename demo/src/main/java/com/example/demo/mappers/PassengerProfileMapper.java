package com.example.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.api.dto.PassengerProfileDtos;
import com.example.demo.domain.entities.PassengerProfile;

@Mapper(componentModel = "spring")
public interface PassengerProfileMapper {
    PassengerProfileDtos toDTO(PassengerProfile passengerProfile);

    @Mapping(target = "id", ignore = true)
    PassengerProfile toEntity(PassengerProfileDtos passengerProfileDTO);
}