package com.example.demo.api.dto;

import java.io.Serializable;
import java.util.List;

import com.example.demo.domain.entities.Flight;

import jakarta.validation.constraints.NotBlank;

public class AirportDtos {
    public record CreateAirportDTO(@NotBlank String name,@NotBlank String code,@NotBlank String city)
            implements Serializable {
    }

    public record UpdateAirportDTO(Long id, String name, String code, String city) implements Serializable {
    }

    public record AirportResponseDTO(Long id, String name, String code, String city, List<Flight> flights,
            List<Flight> arrivals) implements Serializable {
    }
}
