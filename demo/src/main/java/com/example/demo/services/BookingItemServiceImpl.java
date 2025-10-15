package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.api.dto.BookingItemDtos;
import com.example.demo.domain.repositories.BookingItemRepository;
import com.example.demo.domain.repositories.BookingRepository;
import com.example.demo.domain.repositories.FlightRepository;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.mappers.BookingItemMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingItemServiceImpl implements BookingItemService {

    private final BookingItemRepository bookingItemRepo;
    private final BookingRepository bookingRepo;
    private final FlightRepository flightRepo;
    private final BookingItemMapper bookingItemMapper; 

    @Override
    public BookingItemDtos.BookingItemResponseDTO create(BookingItemDtos.CreateBookingItemDTO req) {
        var booking = bookingRepo.findById(req.bookingId())
                .orElseThrow(() -> new NotFoundException("Booking %d not found".formatted(req.bookingId())));
        var flight = flightRepo.findById(req.flightId())
                .orElseThrow(() -> new NotFoundException("Flight %d not found".formatted(req.flightId())));

        var entity = bookingItemMapper.toEntity(req);
        entity.setBooking(booking);
        entity.setFlight(flight);

        return bookingItemMapper.toResponse(bookingItemRepo.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public BookingItemDtos.BookingItemResponseDTO get(Long id) {
        return bookingItemRepo.findById(id)
                .map(bookingItemMapper::toResponse) 
                .orElseThrow(() -> new NotFoundException("BookingItem %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingItemDtos.BookingItemResponseDTO> list() {
        return bookingItemRepo.findAll()
                .stream()
                .map(bookingItemMapper::toResponse) 
                .toList();
    }

    @Override
    public BookingItemDtos.BookingItemResponseDTO update(Long id, BookingItemDtos.UpdateBookingItemDTO req) {
        var bookingItem = bookingItemRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("BookingItem %d not found".formatted(id)));

        if (req.bookingId() != null) {
            var booking = bookingRepo.findById(req.bookingId())
                    .orElseThrow(() -> new NotFoundException("Booking %d not found".formatted(req.bookingId())));
            bookingItem.setBooking(booking);
        }

        if (req.flightId() != null) {
            var flight = flightRepo.findById(req.flightId())
                    .orElseThrow(() -> new NotFoundException("Flight %d not found".formatted(req.flightId())));
            bookingItem.setFlight(flight);
        }

        bookingItemMapper.patch(bookingItem, req); 

        return bookingItemMapper.toResponse(bookingItemRepo.save(bookingItem));
    }

    @Override
    public void delete(Long id) {
        bookingItemRepo.deleteById(id);
    }
}

