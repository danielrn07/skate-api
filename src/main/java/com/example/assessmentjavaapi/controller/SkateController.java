package com.example.assessmentjavaapi.controller;

import com.example.assessmentjavaapi.exception.ResourceNotFoundException;
import com.example.assessmentjavaapi.model.Skate;
import com.example.assessmentjavaapi.service.SkateService;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/skate")
public class SkateController {
    @Autowired
    SkateService skateService;

    Logger logger = LoggerFactory.getLogger(SkateController.class);

    @GetMapping
    public List<Skate> getAll(@RequestParam(required = false, defaultValue = "50") int size,
                              @RequestParam(required = false, defaultValue = "") String sort,
                              @RequestParam(required = false, defaultValue = "") String order) {
        logger.info("GET ALL SKATES");
        return skateService.getAll(size, sort, order);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable long id) {
        logger.info("GET SKATE");

        try {
            Skate skate = skateService.getById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Skate inexistente"));

            return ResponseEntity.ok(skate);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody Skate skate) {
        logger.info("INSERT SKATE");

        try {
            Skate newSkate = skateService.insert(skate.getId(), skate);
            return ResponseEntity.ok(newSkate);
        } catch (IllegalStateException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        logger.info("DELETE SKATE");

        try {
            Skate skateRemoved = skateService.delete(id);
            return ResponseEntity.ok(skateRemoved);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody Skate skate) {
        logger.info("UPDATE SKATE");
        skateService.update(id, skate);
    }
}
