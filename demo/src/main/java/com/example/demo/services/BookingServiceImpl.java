package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.api.dto.BookingDtos;
import com.example.demo.domain.entities.Booking;
import com.example.demo.domain.entities.Flight;
import com.example.demo.domain.entities.Passenger;
import com.example.demo.domain.repositories.BookingRepository;
import com.example.demo.domain.repositories.FlightRepository;
import com.example.demo.domain.repositories.PassengerRepository;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.mappers.BookingMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepo;
    private final PassengerRepository passengerRepo;
    private final FlightRepository flightRepo;

    @Override
    public BookingDtos.BookingResponseDTO create(BookingDtos.CreateBookingDTO req) {
        Passenger passenger = passengerRepo.findById(req.passengerId())
                .orElseThrow(() -> new NotFoundException("Passenger %d not found".formatted(req.passengerId())));

        Flight flight = flightRepo.findById(req.flightId())
                .orElseThrow(() -> new NotFoundException("Flight %d not found".formatted(req.flightId())));

        Booking booking = BookingMapper.toEntity(req, passenger, flight);
        return BookingMapper.toResponse(bookingRepo.save(booking));
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDtos.BookingResponseDTO get(Long id) {
        return bookingRepo.findById(id)
                .map(BookingMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Booking %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDtos.BookingResponseDTO> list() {
        return bookingRepo.findAll()
                .stream()
                .map(BookingMapper::toResponse)
                .toList();
    }

    @Override
    public BookingDtos.BookingResponseDTO update(Long id, BookingDtos.UpdateBookingDTO req) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking %d not found".formatted(id)));

        Passenger passenger = req.passengerId() != null
                ? passengerRepo.findById(req.passengerId())
                .orElseThrow(() -> new NotFoundException("Passenger %d not found".formatted(req.passengerId())))
                : null;

        Flight flight = req.flightId() != null
                ? flightRepo.findById(req.flightId())
                .orElseThrow(() -> new NotFoundException("Flight %d not found".formatted(req.flightId())))
                : null;

        BookingMapper.patch(booking, req, passenger, flight);
        return BookingMapper.toResponse(bookingRepo.save(booking));
    }

    @Override
    public void delete(Long id) {
        bookingRepo.deleteById(id);
    }
}