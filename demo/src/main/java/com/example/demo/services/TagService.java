package com.example.demo.services;

import java.util.List;

import com.example.demo.api.dto.TagDtos.CreateTagDTO;
import com.example.demo.api.dto.TagDtos.TagResponseDTO;
import com.example.demo.api.dto.TagDtos.UpdateTagDTO;

public interface TagService {
    TagResponseDTO create(CreateTagDTO req);
    TagResponseDTO get(Long id);
    void delete(Long id);
    TagResponseDTO update(Long id, UpdateTagDTO req);
    List<TagResponseDTO> list();
    
}