package com.example.demo.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.entities.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    List<Tag> findByNameIn(List<String> names);
}
