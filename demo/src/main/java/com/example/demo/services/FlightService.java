package com.example.demo.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.api.dto.FlightDtos.CreateFlightDTO;
import com.example.demo.api.dto.FlightDtos.FlightResponseDTO;
import com.example.demo.api.dto.FlightDtos.UpdateFlightDTO;

public interface FlightService {

    FlightResponseDTO create(Long airlineId, CreateFlightDTO req);
    FlightResponseDTO get(Long id);
    void delete(Long id);
    FlightResponseDTO update(Long id,   UpdateFlightDTO req);
    Page<FlightResponseDTO> list(Pageable pageable);
    FlightResponseDTO addTag(Long flightId, Long tagId);
    FlightResponseDTO removeTag(Long flightId, Long tagId);

}
