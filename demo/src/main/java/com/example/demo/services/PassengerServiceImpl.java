package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.api.dto.PassengerDtos.CreatePassengerDTO;
import com.example.demo.api.dto.PassengerDtos.PassengerResponseDTO;
import com.example.demo.api.dto.PassengerDtos.UpdatePassengerDTO;
import com.example.demo.domain.entities.Passenger;
import com.example.demo.domain.entities.PassengerProfile;
import com.example.demo.domain.repositories.PassengerProfileRepository;
import com.example.demo.domain.repositories.PassengerRepository;
import com.example.demo.services.mappers.PassengerMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerProfileRepository passengerProfileRepository;
    private final PassengerMapper passengerMapper;

    @Override
    public PassengerResponseDTO create(CreatePassengerDTO req) {
        PassengerProfile profile = passengerProfileRepository.findById(req.passengerProfileId())
                .orElseThrow(() -> new EntityNotFoundException("PassengerProfile not found with id " + req.passengerProfileId()));

        Passenger passenger = passengerMapper.toEntity(req);
        passenger = new Passenger(passenger.getId(), passenger.getName(), passenger.getEmail(), profile);

        Passenger saved = passengerRepository.save(passenger);
        return passengerMapper.toResponse(saved);
    }

    @Override
    public PassengerResponseDTO get(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id));
        return passengerMapper.toResponse(passenger);
    }

    @Override
    public void delete(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new EntityNotFoundException("Passenger not found with id " + id);
        }
        passengerRepository.deleteById(id);
    }

    @Override
    public PassengerResponseDTO update(Long id, UpdatePassengerDTO req) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id));

        PassengerProfile profile = null;
        if (req.passengerProfileId() != null) {
            profile = passengerProfileRepository.findById(req.passengerProfileId())
                    .orElseThrow(() -> new EntityNotFoundException("PassengerProfile not found with id " + req.passengerProfileId()));
        }

        passengerMapper.patch(passenger, req);

        if (profile != null) {
            passenger = new Passenger(passenger.getId(), passenger.getName(), passenger.getEmail(), profile);
        }

        Passenger updated = passengerRepository.save(passenger);
        return passengerMapper.toResponse(updated);
    }

    @Override
    public List<PassengerResponseDTO> list() {
        return passengerRepository.findAll().stream()
                .map(passengerMapper::toResponse)
                .toList();
    }
}
