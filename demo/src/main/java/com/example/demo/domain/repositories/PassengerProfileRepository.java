package com.example.demo.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.entities.PassengerProfile;

public interface PassengerProfileRepository extends JpaRepository<PassengerProfile, Long> {


}