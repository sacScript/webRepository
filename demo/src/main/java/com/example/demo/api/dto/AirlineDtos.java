package com.example.demo.api.dto;

import java.io.Serializable;

public class AirlineDtos {
    public record AirlineCreateDto(
            String code,
            String name) implements Serializable {
    }

    public record AirlineUpdateDto(
            String name) implements Serializable {
    }
    
    public record AirlineResponse(
            Long id,
            String code,
            String name) implements Serializable {
    }
}
