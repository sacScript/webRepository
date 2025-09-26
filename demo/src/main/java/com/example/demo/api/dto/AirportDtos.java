package com.example.demo.api.dto;

import java.io.Serializable;

public class AirportDtos {
    public record CreateAirportDTO(String name, String code) implements Serializable {
    }

    public record UpdateAirportDTO(String name, String code) implements Serializable {
    }

    public record AirportResponseDTO(Long id, String name, String code) implements Serializable {
    }
}
