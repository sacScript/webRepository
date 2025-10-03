package com.example.demo.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.entities.Passenger;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Optional<Passenger> findByEmailIgnoreCase(String email);

    @Query("SELECT p FROM Passenger p LEFT JOIN FETCH p.profile WHERE LOWER(p.email) = LOWER(:email)")
    Optional<Passenger> findByEmailIgnoreCaseWithProfile(String email);
}