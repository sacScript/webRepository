package com.example.demo.services.mappers;



import com.example.demo.api.dto.TagDtos;
import com.example.demo.domain.entities.Tag;

public class TagMapper {

    public static Tag toEntity(TagDtos.CreateTagDTO req) {
        return Tag.builder()
                .name(req.name())
                .build();
    }

    public static TagDtos.TagResponseDTO toResponse(Tag tag) {
        return new TagDtos.TagResponseDTO(
                tag.getId(),
                tag.getName()
        );
    }

    public static void patch(Tag tag, TagDtos.UpdateTagDTO req) {
        if (req.name() != null) tag.setName(req.name());
    }
}
