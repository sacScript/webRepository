package com.example.demo.services.mappers;



import com.example.demo.api.dto.PassengerProfileDtos;
import com.example.demo.domain.entities.Passenger;
import com.example.demo.domain.entities.PassengerProfile;

public class PassengerProfileMapper {

    public static PassengerProfile toEntity(PassengerProfileDtos.CreatePassengerProfileDTO req, Passenger passenger) {
        return PassengerProfile.builder()
                .phone(req.phone())
                .countryCode(req.countryCode())
                .passenger(passenger)
                .build();
    }

    public static PassengerProfileDtos.PassengerProfileResponseDTO toResponse(PassengerProfile profile) {
        return new PassengerProfileDtos.PassengerProfileResponseDTO(
                profile.getId(),
                profile.getPhone(),
                profile.getCountryCode(),
                profile.getPassenger() != null ? profile.getPassenger().getId() : null
        );
    }

    public static void patch(PassengerProfile profile, PassengerProfileDtos.UpdatePassengerProfileDTO req, Passenger passenger) {
        if (req.phone() != null) profile.setPhone(req.phone());
        if (req.countryCode() != null) profile.setCountryCode(req.countryCode());
        if (req.passengerId() != null && passenger != null) profile.setPassenger(passenger);
    }
}
