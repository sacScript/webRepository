package com.example.demo.api;


import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.api.dto.BookingDtos.BookingItemCreateDTO;
import com.example.demo.api.dto.BookingDtos.BookingItemResponseDTO;
import com.example.demo.services.BookingItemService;

@RestController
@RequestMapping("/api/bookings/{bookingId}/items")
@RequiredArgsConstructor
@Validated
public class BookingItemController {

    private final BookingItemService service;

    @PostMapping
    public ResponseEntity<BookingItemResponseDTO> add(@PathVariable Long bookingId,
                                                       @Valid @RequestBody BookingItemCreateDTO req,
                                                       UriComponentsBuilder uriBuilder) {
        var body = service.addItem(bookingId, req);
        var location = uriBuilder
                .path("/api/bookings/{bookingId}/items/{itemId}")
                .buildAndExpand(bookingId, body.id()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping
    public ResponseEntity<List<BookingItemResponseDTO>> list(@PathVariable Long bookingId) {
        return ResponseEntity.ok(service.listByBooking(bookingId));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> delete(@PathVariable Long bookingId, @PathVariable Long itemId) {
        service.removeItem(itemId);
        return ResponseEntity.noContent().build();
    }
}


