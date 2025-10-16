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

import com.example.demo.api.dto.AirportDtos.AirportResponseDTO;
import com.example.demo.api.dto.AirportDtos.CreateAirportDTO;
import com.example.demo.api.dto.AirportDtos.UpdateAirportDTO;
import com.example.demo.services.AirportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
@Validated
public class AirportController {

    private final AirportService service;

    @PostMapping
    public ResponseEntity<AirportResponseDTO> create(@Valid @RequestBody CreateAirportDTO req,
            UriComponentsBuilder uriBuilder) {

        var body = service.create(req);
        var uri = uriBuilder.path("/api/airports/{id}").buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(uri).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<AirportResponseDTO>> list(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var result = service.list(PageRequest.of(page, size, Sort.by("id").ascending()));
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AirportResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody UpdateAirportDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
