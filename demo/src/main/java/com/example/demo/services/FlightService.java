package com.example.demo.services;

import java.util.List;

import com.example.demo.api.dto.FlightDtos.CreateFlightDTO;
import com.example.demo.api.dto.FlightDtos.FlightResponseDTO;
import com.example.demo.api.dto.FlightDtos.UpdateFlightDTO;

public interface FlightService {

    FlightResponseDTO create(CreateFlightDTO req);
    FlightResponseDTO get(Long id);
    void delete(Long id);
    FlightResponseDTO update(Long id,   UpdateFlightDTO req);
    List<FlightResponseDTO> list();


}
