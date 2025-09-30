package com.example.demo.services.mappers;

import com.example.demo.api.dto.AirlineDtos;
import com.example.demo.domain.entities.Airline;

public class AirlineMapper {

    public static Airline toEntity(AirlineDtos.AirlineCreateDto req){
        return Airline.builder().name(req.name()).build();

    }

    public static AirlineDtos.AirlineResponse toResponse(Airline airline){
        return new AirlineDtos.AirlineResponse(airline.getId(), airline.getCode(), airline.getName());
    }

   
}   