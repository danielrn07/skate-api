package com.example.assessmentjavaapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shipping {
    private String cepOrigin;
    private String cepDestination;
    private double price;
    private int deliveryInDays;
}
