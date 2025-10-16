package com.example.demo.api.dto;

import jakarta.validation.constraints.Size;
import java.io.Serializable;

public class PassengerDtos {
    public record CreatePassengerDTO(String fullName, String email, PassengerProfileDTO profile) implements Serializable {
    }

    public record UpdatePassengerDTO(Long id, String fullName, String email, PassengerProfileDTO profile)
            implements Serializable {
    }

    public record PassengerResponseDTO(Long id, String fullName, String email, PassengerProfileDTO profile)
            implements Serializable {
    }

    public record PassengerProfileDTO(@Size(max = 24) String phone, @Size(max = 5) String countryCode) implements Serializable {
    }
}
