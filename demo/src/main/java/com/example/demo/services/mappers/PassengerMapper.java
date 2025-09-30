package com.example.demo.services.mappers;



import com.example.demo.api.dto.PassengerDtos;
import com.example.demo.domain.entities.Passenger;
import com.example.demo.domain.entities.PassengerProfile;

public class PassengerMapper {

    public static Passenger toEntity(PassengerDtos.CreatePassengerDTO req, PassengerProfile profile) {
        return Passenger.builder()
                .fullName(req.fullName())
                .email(req.email())
                .profile(profile)
                .build();
    }

    public static PassengerDtos.PassengerResponseDTO toResponse(Passenger passenger) {
        return new PassengerDtos.PassengerResponseDTO(
                passenger.getId(),
                passenger.getFullName(),
                passenger.getEmail(),
                passenger.getProfile() != null ? passenger.getProfile().getId() : null
        );
    }

    public static void patch(Passenger passenger, PassengerDtos.UpdatePassengerDTO req, PassengerProfile profile) {
        if (req.fullName() != null) passenger.setFullName(req.fullName());
        if (req.email() != null) passenger.setEmail(req.email());
        if (req.passengerProfileId() != null && profile != null) passenger.setProfile(profile);
    }
}
