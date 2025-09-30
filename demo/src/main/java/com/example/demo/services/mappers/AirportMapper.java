package com.example.demo.services.mappers;



import com.example.demo.api.dto.AirportDtos;
import com.example.demo.domain.entities.Airport;

public class AirportMapper {

    
    public static Airport toEntity(AirportDtos.CreateAirportDTO req) {
        return Airport.builder()
                .name(req.name())
                .code(req.code())
                .build();
    }

    public static AirportDtos.AirportResponseDTO toResponse(Airport airport) {
        return new AirportDtos.AirportResponseDTO(
                airport.getId(),
                airport.getName(),
                airport.getCode()
                
        );
    }

   
    public static void patch(Airport airport, AirportDtos.UpdateAirportDTO req) {
        if (req.name() != null) airport.setName(req.name());
        if (req.code() != null) airport.setCode(req.code());
    }
}
