package com.example.demo.services;

import java.util.List;

import com.example.demo.api.dto.PassengerDtos.CreatePassengerDTO;
import com.example.demo.api.dto.PassengerDtos.PassengerResponseDTO;
import com.example.demo.api.dto.PassengerDtos.UpdatePassengerDTO;

public interface PassengerService{

PassengerResponseDTO create(CreatePassengerDTO req);
PassengerResponseDTO get(Long id);
void delete(Long id);
PassengerResponseDTO update(Long id, UpdatePassengerDTO req);
List<PassengerResponseDTO> list();


}