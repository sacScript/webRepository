package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.api.dto.PassengerProfileDtos.CreatePassengerProfileDTO;
import com.example.demo.api.dto.PassengerProfileDtos.PassengerProfileResponseDTO;
import com.example.demo.api.dto.PassengerProfileDtos.UpdatePassengerProfileDTO;
import com.example.demo.domain.entities.Passenger;
import com.example.demo.domain.entities.PassengerProfile;
import com.example.demo.domain.repositories.PassengerProfileRepository;
import com.example.demo.domain.repositories.PassengerRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PassengerProfileServiceImpl implements PassengerProfileService {

    private final PassengerProfileRepository passengerProfileRepository;
    private final PassengerRepository passengerRepository;

    @Override
    public PassengerProfileResponseDTO create(CreatePassengerProfileDTO req) {
        Passenger passenger = passengerRepository.findById(req.passengerId())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + req.passengerId()));

        PassengerProfile profile = new PassengerProfile();
        profile.setPhone(req.phone());
        profile.setCountryCode(req.countryCode());
        profile.setPassenger(passenger);

        PassengerProfile saved = passengerProfileRepository.save(profile);
        return toResponse(saved);
    }

    @Override
    public PassengerProfileResponseDTO get(Long id) {
        PassengerProfile profile = passengerProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PassengerProfile not found with id " + id));
        return toResponse(profile);
    }

    @Override
    public void delete(Long id) {
        if (!passengerProfileRepository.existsById(id)) {
            throw new EntityNotFoundException("PassengerProfile not found with id " + id);
        }
        passengerProfileRepository.deleteById(id);
    }

    @Override
    public PassengerProfileResponseDTO update(Long id, UpdatePassengerProfileDTO req) {
        PassengerProfile profile = passengerProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PassengerProfile not found with id " + id));

        if (req.phone() != null) {
            profile.setPhone(req.phone());
        }
        if (req.countryCode() != null) {
            profile.setCountryCode(req.countryCode());
        }
        if (req.passengerId() != null) {
            Passenger passenger = passengerRepository.findById(req.passengerId())
                    .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + req.passengerId()));
            profile.setPassenger(passenger);
        }

        PassengerProfile updated = passengerProfileRepository.save(profile);
        return toResponse(updated);
    }

    @Override
    public List<PassengerProfileResponseDTO> list() {
        return passengerProfileRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private PassengerProfileResponseDTO toResponse(PassengerProfile profile) {
        return new PassengerProfileResponseDTO(
                profile.getId(),
                profile.getPhone(),
                profile.getCountryCode(),
                profile.getPassenger() != null ? profile.getPassenger().getId() : null
        );
    }
}
