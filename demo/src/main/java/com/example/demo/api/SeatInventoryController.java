package com.example.demo.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.api.dto.SeatInventoryDtos.CreateSeatInventoryDTO;
import com.example.demo.api.dto.SeatInventoryDtos.SeatInventoryResponseDTO;
import com.example.demo.api.dto.SeatInventoryDtos.UpdateSeatInventoryDTO;
import com.example.demo.services.SeatInventoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class SeatInventoryController {

    private final SeatInventoryService service;

    @PostMapping("/flights/{flightId}/seatinventories")
    public ResponseEntity<SeatInventoryResponseDTO> create(@PathVariable Long flightId, @Valid @RequestBody CreateSeatInventoryDTO req,
            UriComponentsBuilder uriBuilder) {

        var body = service.create(flightId, req);
        var uri = uriBuilder.path("/api/seatinventories/{id}").buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(uri).body(body);
    }

    @GetMapping("/seatinventories/{id}")
    public ResponseEntity<SeatInventoryResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/flights/{flightId}/seatinventories")
    public ResponseEntity<List<SeatInventoryResponseDTO>> listByFlight(@PathVariable Long flightId,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        var result = service.listByFlight(flightId);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/seatinventories/{id}")
    public ResponseEntity<SeatInventoryResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody UpdateSeatInventoryDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/seatinventories/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
