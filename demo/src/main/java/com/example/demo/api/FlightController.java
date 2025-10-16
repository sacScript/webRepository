package com.example.demo.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

import com.example.demo.api.dto.FlightDtos.CreateFlightDTO;
import com.example.demo.api.dto.FlightDtos.FlightResponseDTO;
import com.example.demo.api.dto.FlightDtos.UpdateFlightDTO;
import com.example.demo.services.FlightService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class FlightController {

    private final FlightService service;

    @PostMapping("/airlines/{airlineId}/flights")
    public ResponseEntity<FlightResponseDTO> create(@PathVariable Long airlineId, @Valid @RequestBody CreateFlightDTO req,
            UriComponentsBuilder uriBuilder) {

        var body = service.create(airlineId,req);
        var uri = uriBuilder.path("/api/flights/{id}").buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(uri).body(body);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<FlightResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping("/flights/{flightId}/tags/{tagId}")
    public ResponseEntity<FlightResponseDTO> addTag(@PathVariable Long flightId, @PathVariable Long tagId) {
        return ResponseEntity.ok(service.addTag(flightId, tagId));
    }

    @DeleteMapping("/flights/{flightId}/tags/{tagId}")
    public ResponseEntity<FlightResponseDTO> removeTag(@PathVariable Long flightId, @PathVariable Long tagId) {
        return ResponseEntity.ok(service.removeTag(flightId, tagId));
    }

    @GetMapping("/flights")
    public ResponseEntity<Page<FlightResponseDTO>> list(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var result = service.list(PageRequest.of(page, size, Sort.by("id").ascending()));
        return ResponseEntity.ok(result);
    } 

    @PatchMapping("/flights/{id}")
    public ResponseEntity<FlightResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody UpdateFlightDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/flights/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
