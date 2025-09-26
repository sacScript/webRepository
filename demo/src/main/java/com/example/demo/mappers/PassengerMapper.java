package com.example.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.api.dto.PassengerDtos;
import com.example.demo.domain.entities.Passenger;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    PassengerDtos toDTO(Passenger passenger);
    @Mapping(target = "id", ignore = true)     
    Passenger toEntity(PassengerDtos passengerDTO);
}
