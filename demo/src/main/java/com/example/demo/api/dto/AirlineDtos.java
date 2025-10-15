package com.example.demo.api.dto;

import java.io.Serializable;
import java.util.List;

import com.example.demo.domain.entities.Flight;

import jakarta.validation.constraints.NotBlank;

public class AirlineDtos {
        public record AirlineCreateDto(
                        @NotBlank String code,
                        @NotBlank String name) implements Serializable {
        }

        public record AirlineUpdateDto(
                        Long id,
                        String code,
                        String name) implements Serializable {
        }

        public record AirlineResponse(
                        Long id,
                        String code,
                        String name,
                        List<Flight> flights) implements Serializable {
        }
}
