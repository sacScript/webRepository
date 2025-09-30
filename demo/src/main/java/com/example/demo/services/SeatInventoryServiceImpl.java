package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.api.dto.SeatInventoryDtos.CreateSeatInventoryDTO;
import com.example.demo.api.dto.SeatInventoryDtos.SeatInventoryResponseDTO;
import com.example.demo.api.dto.SeatInventoryDtos.UpdateSeatInventoryDTO;
import com.example.demo.domain.entities.Flight;
import com.example.demo.domain.entities.SeatInventory;
import com.example.demo.domain.repositories.FlightRepository;
import com.example.demo.domain.repositories.SeatInventoryRepository;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatInventoryServiceImpl implements SeatInventoryService {

    private final SeatInventoryRepository seatInventoryRepository;
    private final FlightRepository flightRepository;

    @Override
    public SeatInventoryResponseDTO create(CreateSeatInventoryDTO req) {
        Flight flight = flightRepository.findById(req.flightId())
                .orElseThrow(() -> new EntityNotFoundException("Flight not found with id " + req.flightId()));

        SeatInventory seatInventory = SeatInventoryMapper.toEntity(req, flight);
        SeatInventory saved = seatInventoryRepository.save(seatInventory);

        return SeatInventoryMapper.toResponse(saved);
    }

    @Override
    public SeatInventoryResponseDTO get(Long id) {
        SeatInventory seatInventory = seatInventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SeatInventory not found with id " + id));
        return SeatInventoryMapper.toResponse(seatInventory);
    }

    @Override
    public void delete(Long id) {
        if (!seatInventoryRepository.existsById(id)) {
            throw new EntityNotFoundException("SeatInventory not found with id " + id);
        }
        seatInventoryRepository.deleteById(id);
    }

    @Override
    public SeatInventoryResponseDTO update(Long id, UpdateSeatInventoryDTO req) {
        SeatInventory seatInventory = seatInventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SeatInventory not found with id " + id));

        Flight flight = null;
        if (req.flightId() != null) {
            flight = flightRepository.findById(req.flightId())
                    .orElseThrow(() -> new EntityNotFoundException("Flight not found with id " + req.flightId()));
        }

        SeatInventoryMapper.patch(seatInventory, req, flight);
        SeatInventory updated = seatInventoryRepository.save(seatInventory);

        return SeatInventoryMapper.toResponse(updated);
    }

    @Override
    public List<SeatInventoryResponseDTO> list() {
        return seatInventoryRepository.findAll().stream()
                .map(SeatInventoryMapper::toResponse)
                .collect(Collectors.toList());
    }
}
