package com.example.demo.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.api.dto.TagDtos;
import com.example.demo.domain.entities.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    
    @Mapping(target = "id", ignore = true)
    Tag toEntity(TagDtos.CreateTagDTO dto);

    TagDtos.TagResponseDTO toResponse(Tag Tag);

    void patch(@MappingTarget Tag Tag, TagDtos.UpdateTagDTO dto);
}
