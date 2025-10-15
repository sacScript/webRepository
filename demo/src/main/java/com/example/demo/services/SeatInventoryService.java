package com.example.demo.services;


import java.util.List;

import com.example.demo.api.dto.SeatInventoryDtos.CreateSeatInventoryDTO;
import com.example.demo.api.dto.SeatInventoryDtos.SeatInventoryResponseDTO;
import com.example.demo.api.dto.SeatInventoryDtos.UpdateSeatInventoryDTO;

public interface SeatInventoryService {

    SeatInventoryResponseDTO create(CreateSeatInventoryDTO req);
    SeatInventoryResponseDTO get(Long id);
    void delete(Long id);
    SeatInventoryResponseDTO update(Long id, UpdateSeatInventoryDTO req);
    List<SeatInventoryResponseDTO> list();
}