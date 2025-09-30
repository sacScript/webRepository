package com.example.demo.services;

import java.util.List;

import com.example.demo.api.dto.AirportDtos.AirportResponseDTO;
import com.example.demo.api.dto.AirportDtos.CreateAirportDTO;
import com.example.demo.api.dto.AirportDtos.UpdateAirportDTO;

public interface AirportService {
    
    AirportResponseDTO create(CreateAirportDTO req);
    AirportResponseDTO get(Long id);
    List<AirportResponseDTO> list();
    AirportResponseDTO update(Long id, UpdateAirportDTO req);
    void delete(Long id);

}