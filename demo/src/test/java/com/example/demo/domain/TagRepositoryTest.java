package com.example.demo.domain;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.domain.entities.Tag;
import com.example.demo.domain.repositories.TagRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TagRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    void testFindByName() {
        // Arrange
        Tag tag = new Tag(null, "EcoFriendly", null);
        tagRepository.save(tag);

        // Act
        Optional<Tag> foundTag = tagRepository.findByName("EcoFriendly");

        // Assert
        assertThat(foundTag).isPresent();
        assertThat(foundTag.get().getName()).isEqualTo("EcoFriendly");
    }

    @Test
    void testFindByNameIn() {
        // Arrange
        Tag tag1 = new Tag(null, "Promo", null);
        Tag tag2 = new Tag(null, "Oferta", null);
        tagRepository.saveAll(List.of(tag1, tag2));

        // Act
        List<Tag> foundTags = tagRepository.findByNameIn(List.of("Promo", "Oferta", "Inexistente"));

        // Assert
        assertThat(foundTags).hasSize(2);
        assertThat(foundTags).extracting(Tag::getName).containsExactlyInAnyOrder("Promo", "Oferta");
    }
}