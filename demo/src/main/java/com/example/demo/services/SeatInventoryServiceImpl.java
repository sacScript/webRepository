package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.api.dto.SeatInventoryDtos.CreateSeatInventoryDTO;
import com.example.demo.api.dto.SeatInventoryDtos.SeatInventoryResponseDTO;
import com.example.demo.api.dto.SeatInventoryDtos.UpdateSeatInventoryDTO;
import com.example.demo.domain.entities.Flight;
import com.example.demo.domain.entities.SeatInventory;
import com.example.demo.domain.repositories.FlightRepository;
import com.example.demo.domain.repositories.SeatInventoryRepository;
import com.example.demo.services.mappers.SeatInventoryMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatInventoryServiceImpl implements SeatInventoryService {

    private final SeatInventoryRepository seatInventoryRepository;
    private final FlightRepository flightRepository;
    private final SeatInventoryMapper seatInventoryMapper;

    @Override
    public SeatInventoryResponseDTO create(CreateSeatInventoryDTO req) {
        Flight flight = flightRepository.findById(req.flightId())
                .orElseThrow(() -> new EntityNotFoundException("Flight not found with id " + req.flightId()));

        SeatInventory seatInventory = seatInventoryMapper.toEntity(req);
        seatInventory.setFlight(flight);

        SeatInventory saved = seatInventoryRepository.save(seatInventory);
        return seatInventoryMapper.toResponse(saved);
    }

    @Override
    public SeatInventoryResponseDTO get(Long id) {
        SeatInventory seatInventory = seatInventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SeatInventory not found with id " + id));
        return seatInventoryMapper.toResponse(seatInventory);
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

        seatInventoryMapper.patch(seatInventory, req);

        if (flight != null) {
            seatInventory.setFlight(flight);
        }

        SeatInventory updated = seatInventoryRepository.save(seatInventory);
        return seatInventoryMapper.toResponse(updated);
    }

    @Override
    public List<SeatInventoryResponseDTO> list() {
        return seatInventoryRepository.findAll().stream()
                .map(seatInventoryMapper::toResponse)
                .toList();
    }
}

