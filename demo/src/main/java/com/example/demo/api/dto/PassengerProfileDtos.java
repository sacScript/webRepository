package com.example.demo.api.dto;

import java.io.Serializable;

public class PassengerProfileDtos {
    public record CreatePassengerProfileDTO(String phone, String countryCode, Long passengerId)
            implements Serializable {
    }

    public record UpdatePassengerProfileDTO(Long id, String phone, String countryCode, Long passengerId)
            implements Serializable {
    }

    public record PassengerProfileResponseDTO(Long id, String phone, String countryCode, Long passengerId)
            implements Serializable {
    }
}
