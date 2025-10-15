package com.example.demo.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.api.dto.AirlineDtos.AirlineCreateDto;
import com.example.demo.api.dto.AirlineDtos.AirlineResponse;
import com.example.demo.api.dto.AirlineDtos.AirlineUpdateDto;

public interface AirlineService {
    AirlineResponse create(AirlineCreateDto req);
    AirlineResponse get(Long id);
    Page<AirlineResponse> list(Pageable pageable);
    AirlineResponse update(Long id, AirlineUpdateDto req);
    void delete(Long id);

}