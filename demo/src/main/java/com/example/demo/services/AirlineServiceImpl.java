package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.api.dto.AirlineDtos;
import com.example.demo.api.dto.AirlineDtos.AirlineCreateDto;
import com.example.demo.api.dto.AirlineDtos.AirlineResponse;
import com.example.demo.domain.repositories.AirlineRepository;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.mappers.AirlineMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository repos;

    @Override
    public AirlineResponse create(AirlineCreateDto req) {
        return AirlineMapper.toResponse(
                repos.save(AirlineMapper.toEntity(req))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AirlineResponse get(Long id) {
        return repos.findById(id)
                .map(AirlineMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Airline %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirlineResponse> list() {
        return repos.findAll()
                .stream()
                .map(AirlineMapper::toResponse)
                .toList();
    }

   @Override
public AirlineResponse update(Long id, AirlineDtos.AirlineUpdateDto req) {
    var airline = repos.findById(id)
            .orElseThrow(() -> new NotFoundException("Airline %d not found".formatted(id)));

    airline.setName(req.name());

    return AirlineMapper.toResponse(airline);
}


    @Override
    public void delete(Long id) {
        repos.deleteById(id);
    }
}

