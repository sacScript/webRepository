package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.api.dto.TagDtos.CreateTagDTO;
import com.example.demo.api.dto.TagDtos.TagResponseDTO;
import com.example.demo.api.dto.TagDtos.UpdateTagDTO;
import com.example.demo.domain.entities.Tag;
import com.example.demo.domain.repositories.TagRepository;
import com.example.demo.services.mappers.TagMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagResponseDTO create(CreateTagDTO req) {
        Tag tag = tagMapper.toEntity(req);
        Tag saved = tagRepository.save(tag);
        return tagMapper.toResponse(saved);
    }

    @Override
    public TagResponseDTO get(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id " + id));
        return tagMapper.toResponse(tag);
    }

    @Override
    public void delete(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new EntityNotFoundException("Tag not found with id " + id);
        }
        tagRepository.deleteById(id);
    }

    @Override
    public TagResponseDTO update(Long id, UpdateTagDTO req) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id " + id));

        tagMapper.patch(tag, req);
        Tag updated = tagRepository.save(tag);

        return tagMapper.toResponse(updated);
    }

    @Override
    public List<TagResponseDTO> list() {
        return tagRepository.findAll().stream()
                .map(tagMapper::toResponse)
                .toList();
    }
}
