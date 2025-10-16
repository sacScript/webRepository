package com.example.demo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.api.dto.PassengerDtos.CreatePassengerDTO;
import com.example.demo.api.dto.PassengerDtos.PassengerResponseDTO;
import com.example.demo.api.dto.PassengerDtos.UpdatePassengerDTO;

public interface PassengerService{

PassengerResponseDTO create(CreatePassengerDTO req);
PassengerResponseDTO get(Long id);
void delete(Long id);
PassengerResponseDTO update(Long id, UpdatePassengerDTO req);
Page<PassengerResponseDTO> list(Pageable pageable);


}