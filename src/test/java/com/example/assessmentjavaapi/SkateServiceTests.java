package com.example.assessmentjavaapi;

import com.example.assessmentjavaapi.exception.ResourceNotFoundException;
import com.example.assessmentjavaapi.model.Skate;
import com.example.assessmentjavaapi.service.SkateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class SkateServiceTests {
    @Autowired
    SkateService skateService;

    @Test
    public void testGetAll() {
        List<Skate> skateList = skateService.getAll();
        assertEquals(200, skateList.size());
    }

    @Test
    public void testId() {
        long id = 1L;
        Optional<Skate> skate = skateService.getById(id);
        assertNotNull(skate);
        skate.ifPresent(value -> assertEquals(1L, value.getId()));
        assertTrue(skate.isPresent());

        Optional<Skate> optional = skateService.getById(-100);
        assertTrue(optional.isEmpty());
    }

    @Test
    void testInsertValidSkate() {
        SkateService skateService = new SkateService();

        Skate skate = new Skate();
        skate.setId(201L);
        skate.setName("Lorem Ipsum");

        Skate insertedSkate = skateService.insert(201L, skate);

        assertNotNull(insertedSkate);
        assertEquals(201L, insertedSkate.getId());
    }

    @Test
    void testInsertInvalidSkate() {
        SkateService skateService = new SkateService();

        Skate invalidSkate = null;

        assertThrows(IllegalStateException.class, () -> skateService.insert(1L, invalidSkate));
    }

    @Test
    void testDeleteSkate() {
        SkateService skateService = new SkateService();

        Skate deletedSkate = skateService.delete(1L);

        assertNotNull(deletedSkate);
        assertEquals(1L, deletedSkate.getId());
    }

    @Test
    void testDeleteSkateNotExisting() {
        SkateService skateService = new SkateService();
        assertThrows(ResourceNotFoundException.class, () -> {
            skateService.delete(201L);
        });
    }

    @Test
    void testUpdateSkate() {
        SkateService skateService = new SkateService();

        Skate updatedSkate = new Skate();
        updatedSkate.setName("Lorem Ipsum");

        skateService.update(1L, updatedSkate);

        Skate retrievedSkate = skateService.getById(1L).orElse(null);
        assertNotNull(retrievedSkate);
        assertEquals(updatedSkate.getName(), retrievedSkate.getName());
    }
}
