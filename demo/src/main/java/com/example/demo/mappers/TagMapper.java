package com.example.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.api.dto.TagDtos;
import com.example.demo.domain.entities.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDtos toDTO(Tag tag);

    @Mapping(target = "id", ignore = true)
    Tag toEntity(TagDtos tagDTO);
}