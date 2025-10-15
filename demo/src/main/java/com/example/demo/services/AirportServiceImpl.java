package com.example.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.api.dto.AirportDtos;
import com.example.demo.domain.repositories.AirportRepository;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.mappers.AirportMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AirportServiceImpl implements AirportService {

    private final AirportRepository repo;
    private final AirportMapper airportMapper;

    @Override
    public AirportDtos.AirportResponseDTO create(AirportDtos.CreateAirportDTO req) {
        var airport = airportMapper.toEntity(req);
        return airportMapper.toResponse(repo.save(airport));
    }

    @Override
    @Transactional(readOnly = true)
    public AirportDtos.AirportResponseDTO get(Long id) {
        return repo.findById(id)
                .map(airportMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Airport %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirportDtos.AirportResponseDTO> list() {
        return repo.findAll().stream()
                .map(airportMapper::toResponse)
                .toList();
    }

    @Override
    public AirportDtos.AirportResponseDTO update(Long id, AirportDtos.UpdateAirportDTO req) {
        var airport = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Airport %d not found".formatted(id)));

        airportMapper.patch(airport, req);

        return airportMapper.toResponse(repo.save(airport));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Airport %d not found".formatted(id));
        }
        repo.deleteById(id);
    }
}
