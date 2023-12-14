package com.example.assessmentjavaapi.service;

import com.example.assessmentjavaapi.exception.ResourceNotFoundException;
import com.example.assessmentjavaapi.model.Skate;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SkateService {

    private Map<Long, Skate> skateList = initSkates();

    private Map<Long, Skate> initSkates() {

        Map<Long, Skate> skateList = new HashMap<Long, Skate>();

        for (long i = 1; i <= 200; i++) {
            Faker faker = new Faker();

            String name = faker.lorem().word();
            String description = faker.lorem().sentence();
            String imagePath = faker.internet().image();
            double size = faker.number().randomDouble(1, 20, 40);
            String[] model = new String[]{faker.lorem().word(), faker.lorem().word(), faker.lorem().word()};
            String category = faker.lorem().word();
            String brand = faker.company().name();
            int amount = faker.number().numberBetween(1, 100);
            double price = faker.number().randomDouble(2, 50, 500);

            Skate skate = Skate.builder()
                    .id(i)
                    .name(name)
                    .description(description)
                    .imagePath(imagePath)
                    .size(size)
                    .model(model)
                    .category(category)
                    .brand(brand)
                    .amount(amount)
                    .price(price)
                    .build();

            skateList.put(i, skate);
        }
        return skateList;
    }

    public List<Skate> getAll() {
        return skateList.values().stream().toList();
    }

    public List<Skate> getAll(int size) {
        List<Skate> list = skateList.values().stream().toList();
        return list.subList(0, size);
    }

    public List<Skate> getAll(int size, String sort, String order) {
        if (sort.isEmpty()) {
            return getAll(size);
        } else {
            List<Skate> skates = getAll(size);
            Comparator<Skate> comparator = Comparator.comparing(Skate::getName);
            if (order.equals("desc")) {
                comparator = comparator.reversed();
            }
            return skates.stream().sorted(comparator).toList();
        }
    }

    public Optional<Skate> getById(long id) {
        Skate skate = skateList.get(id);
        if (skate == null) return Optional.empty();
        return Optional.of(skate);
    }

    public Skate insert(long id, Skate skate) {
        if (skateList.containsKey(id)) throw new IllegalStateException("ID já existente.");
        if (skate == null) throw new IllegalStateException("Skate inválido. Preencha os campos necessários");
        if (id < 0) throw new IllegalArgumentException("Não é permitido inserir ID negativo.");
        skateList.put(id, skate);
        return skate;
    }

    public Skate delete(long id) {
        if (!skateList.containsKey(id)) throw new ResourceNotFoundException("Skate inexistente");
        return skateList.remove(id);
    }

    public void update(long id, Skate skate) {
        skateList.remove(id);
        skateList.put(id, skate);
    }
}
