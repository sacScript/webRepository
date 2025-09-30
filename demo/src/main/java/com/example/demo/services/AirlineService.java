package com.example.demo.services;

import java.util.List;

import com.example.demo.api.dto.AirlineDtos.AirlineCreateDto;
import com.example.demo.api.dto.AirlineDtos.AirlineResponse;
import com.example.demo.api.dto.AirlineDtos.AirlineUpdateDto;

public interface AirlineService {
    AirlineResponse create(AirlineCreateDto req);
    AirlineResponse get(Long id);
    List<AirlineResponse> list();
    AirlineResponse update(Long id, AirlineUpdateDto req);
    void delete(Long id);

}