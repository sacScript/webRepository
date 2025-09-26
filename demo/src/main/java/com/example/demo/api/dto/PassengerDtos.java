package com.example.demo.api.dto;

import java.io.Serializable;

public class PassengerDtos {
    public record CreatePassengerDTO(String fullName, String email, Long passengerProfileId) implements Serializable {
    }

    public record UpdatePassengerDTO(Long id, String fullName, String email, Long passengerProfileId)
            implements Serializable {
    }

    public record PassengerResponseDTO(Long id, String fullName, String email, Long passengerProfileId)
            implements Serializable {
    }
}
