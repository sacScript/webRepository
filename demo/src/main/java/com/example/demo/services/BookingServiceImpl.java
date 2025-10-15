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
    private final BookingMapper bookingMapper; 

    @Override
    public BookingDtos.BookingResponseDTO create(BookingDtos.CreateBookingDTO req) {
        Passenger passenger = passengerRepo.findById(req.passengerId())
                .orElseThrow(() -> new NotFoundException(
                        "Passenger %d not found".formatted(req.passengerId())));

        Flight flight = flightRepo.findById(req.flightId())
                .orElseThrow(() -> new NotFoundException(
                        "Flight %d not found".formatted(req.flightId())));

        Booking booking = bookingMapper.toEntity(req);

        booking.setPassenger(passenger);
        booking.setFlight(flight);

        return bookingMapper.toResponse(bookingRepo.save(booking));
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDtos.BookingResponseDTO get(Long id) {
        return bookingRepo.findById(id)
                .map(bookingMapper::toResponse)
                .orElseThrow(() -> new NotFoundException(
                        "Booking %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDtos.BookingResponseDTO> list() {
        return bookingRepo.findAll()
                .stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    @Override
    public BookingDtos.BookingResponseDTO update(Long id, BookingDtos.UpdateBookingDTO req) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Booking %d not found".formatted(id)));

        Passenger passenger = req.passengerId() != null
                ? passengerRepo.findById(req.passengerId())
                    .orElseThrow(() -> new NotFoundException(
                            "Passenger %d not found".formatted(req.passengerId())))
                : null;

        Flight flight = req.flightId() != null
                ? flightRepo.findById(req.flightId())
                    .orElseThrow(() -> new NotFoundException(
                            "Flight %d not found".formatted(req.flightId())))
                : null;

        bookingMapper.patch(booking, req);

        if (passenger != null) booking.setPassenger(passenger);
        if (flight != null) booking.setFlight(flight);

        return bookingMapper.toResponse(bookingRepo.save(booking));
    }

    @Override
    public void delete(Long id) {
        bookingRepo.deleteById(id);
    }
}