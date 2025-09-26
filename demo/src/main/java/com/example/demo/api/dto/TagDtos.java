package com.example.demo.api.dto;

import java.io.Serializable;

public class TagDtos {
    public record CreateTagDTO(String name) implements Serializable {
    }

    public record UpdateTagDTO(String name) implements Serializable {
    }

    public record TagResponseDTO(Long id, String name) implements Serializable {
    }
}
