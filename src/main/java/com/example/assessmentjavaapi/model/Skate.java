package com.example.assessmentjavaapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Skate {
    private long id;
    private String name;
    private String description;
    private String imagePath;
    private double size;
    private String[] model;
    private String category;
    private String brand;
    private int amount;
    public double price;
}

