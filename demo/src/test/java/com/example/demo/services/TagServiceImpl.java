package com.example.demo.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.api.dto.TagDtos;
import com.example.demo.domain.entities.Tag;
import com.example.demo.domain.repositories.TagRepository;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    TagRepository tagRepository;

    @InjectMocks
    TagServiceImpl tagService;

    @Test
    void shouldCreateAndReturnResponseDto() {
        var req = new TagDtos.CreateTagDTO("VIP");

        when(tagRepository.save(any())).thenAnswer(inv -> {
            Tag tag = inv.getArgument(0);
            tag.setId(1L);
            return tag;
        });

        var res = tagService.create(req);

        assertThat(res.id()).isEqualTo(1L);
        assertThat(res.name()).isEqualTo("VIP");
        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    void shouldGetById() {
        var tag = Tag.builder().id(2L).name("Premium").build();
        when(tagRepository.findById(2L)).thenReturn(Optional.of(tag));

        var res = tagService.get(2L);

        assertThat(res.id()).isEqualTo(2L);
        assertThat(res.name()).isEqualTo("Premium");
    }

    @Test
    void shouldThrowWhenGetNotFound() {
        when(tagRepository.findById(5L)).thenReturn(Optional.empty());

        try {
            tagService.get(5L);
        } catch (EntityNotFoundException ex) {
            assertThat(ex.getMessage()).isEqualTo("Tag not found with id 5");
        }
    }

    @Test
    void shouldUpdateTag() {
        var existing = Tag.builder().id(3L).name("OldName").build();
        when(tagRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(tagRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var updatedReq = new TagDtos.UpdateTagDTO("NewName");
        var res = tagService.update(3L, updatedReq);

        assertThat(res.id()).isEqualTo(3L);
        assertThat(res.name()).isEqualTo("NewName");
        verify(tagRepository).save(existing);
    }

    @Test
    void shouldDeleteTag() {
        when(tagRepository.existsById(4L)).thenReturn(true);

        tagService.delete(4L);

        verify(tagRepository).deleteById(4L);
    }

    @Test
    void shouldThrowWhenDeleteNotFound() {
        when(tagRepository.existsById(6L)).thenReturn(false);

        try {
            tagService.delete(6L);
        } catch (EntityNotFoundException ex) {
            assertThat(ex.getMessage()).isEqualTo("Tag not found with id 6");
        }
    }

    @Test
    void shouldListAllTags() {
        var tag1 = Tag.builder().id(1L).name("A").build();
        var tag2 = Tag.builder().id(2L).name("B").build();
        when(tagRepository.findAll()).thenReturn(List.of(tag1, tag2));

        var res = tagService.list();

        assertThat(res).hasSize(2);
        assertThat(res.get(0).name()).isEqualTo("A");
        assertThat(res.get(1).name()).isEqualTo("B");
    }
}
