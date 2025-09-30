package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


import com.example.demo.api.dto.FlightDtos.CreateFlightDTO;
import com.example.demo.api.dto.FlightDtos.FlightResponseDTO;
import com.example.demo.api.dto.FlightDtos.UpdateFlightDTO;
import com.example.demo.domain.entities.Airline;
import com.example.demo.domain.entities.Airport;
import com.example.demo.domain.entities.Flight;
import com.example.demo.domain.repositories.AirlineRepository;
import com.example.demo.domain.repositories.AirportRepository;
import com.example.demo.domain.repositories.FlightRepository;
import com.example.demo.services.mappers.FlightMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private final AirlineRepository airlineRepository;

    @Override
    public FlightResponseDTO create(CreateFlightDTO req) {
        Airport origin = airportRepository.findByCode(req.origin())
                .orElseThrow(() -> new EntityNotFoundException("Origin airport not found: " + req.origin()));

        Airport destination = airportRepository.findByCode(req.destination())
                .orElseThrow(() -> new EntityNotFoundException("Destination airport not found: " + req.destination()));

        
        Airline airline = airlineRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No airline found"));

        Flight flight = FlightMapper.toEntity(req, origin, destination, airline);
        return FlightMapper.toResponse(flightRepository.save(flight));
    }

    @Override
    public FlightResponseDTO get(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found with id " + id));
        return FlightMapper.toResponse(flight);
    }

    @Override
    public void delete(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new EntityNotFoundException("Flight not found with id " + id);
        }
        flightRepository.deleteById(id);
    }

    @Override
    public FlightResponseDTO update(Long id, UpdateFlightDTO req) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found with id " + id));

        Airport origin = null;
        Airport destination = null;

        if (req.origin() != null) {
            origin = airportRepository.findByCode(req.origin())
                    .orElseThrow(() -> new EntityNotFoundException("Origin airport not found: " + req.origin()));
        }

        if (req.destination() != null) {
            destination = airportRepository.findByCode(req.destination())
                    .orElseThrow(() -> new EntityNotFoundException("Destination airport not found: " + req.destination()));
        }

        FlightMapper.patch(flight, req, origin, destination);
        return FlightMapper.toResponse(flightRepository.save(flight));
    }

    @Override
    public List<FlightResponseDTO> list() {
        return flightRepository.findAll().stream()
                .map(FlightMapper::toResponse)
                .collect(Collectors.toList());
    }
}