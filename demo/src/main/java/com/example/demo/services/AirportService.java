package com.example.demo.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.api.dto.AirportDtos.AirportResponseDTO;
import com.example.demo.api.dto.AirportDtos.CreateAirportDTO;
import com.example.demo.api.dto.AirportDtos.UpdateAirportDTO;

public interface AirportService {
    
    AirportResponseDTO create(CreateAirportDTO req);
    AirportResponseDTO get(Long id);
    Page<AirportResponseDTO> list(Pageable pageable);
    AirportResponseDTO update(Long id, UpdateAirportDTO req);
    void delete(Long id);

}