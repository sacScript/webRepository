package com.example.demo.services;

import java.util.List;

import com.example.demo.api.dto.PassengerProfileDtos.CreatePassengerProfileDTO;
import com.example.demo.api.dto.PassengerProfileDtos.PassengerProfileResponseDTO;
import com.example.demo.api.dto.PassengerProfileDtos.UpdatePassengerProfileDTO;

public interface PassengerProfileService {
    
PassengerProfileResponseDTO create(CreatePassengerProfileDTO req);
PassengerProfileResponseDTO get(Long id);
void delete(Long id);
PassengerProfileResponseDTO update(Long id, UpdatePassengerProfileDTO req);
List<PassengerProfileResponseDTO> list();



}