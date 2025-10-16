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

import com.example.demo.api.dto.BookingDtos.BookingResponseDTO;
import com.example.demo.api.dto.BookingDtos.CreateBookingDTO;
import com.example.demo.api.dto.BookingDtos.UpdateBookingDTO;
import com.example.demo.services.BookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class BookingController {

    private final BookingService service;

    @PostMapping("/passengers/{passengerId}/bookings")
    public ResponseEntity<BookingResponseDTO> create(@PathVariable Long passengerId, @Valid @RequestBody CreateBookingDTO req,
            UriComponentsBuilder uriBuilder) {

        var body = service.create(passengerId, req);
        var uri = uriBuilder.path("/api/bookings/{id}").buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(uri).body(body);
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<BookingResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/passengers/{email}/bookings")
    public ResponseEntity<Page<BookingResponseDTO>> list(@RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var result = service.listByPassenger(email, PageRequest.of(page, size, Sort.by("id").ascending()));
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/bookings/{id}")
    public ResponseEntity<BookingResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody UpdateBookingDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
