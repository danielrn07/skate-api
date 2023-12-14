package com.example.assessmentjavaapi.service;

import com.example.assessmentjavaapi.exception.ViaCepException;
import com.example.assessmentjavaapi.exception.ViaCepFormatException;
import com.example.assessmentjavaapi.model.Cep;
import com.example.assessmentjavaapi.model.Shipping;
import com.example.assessmentjavaapi.utils.ViaCepUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.MINUTES;

@RestController
@RequestMapping("/cep")
public class ViaCepService {
    static Logger logger = LoggerFactory.getLogger(ViaCepService.class);
    private static final String viaCepUrl = "https://viacep.com.br/ws/";

    @GetMapping
    public static ResponseEntity<Object> findCep(
            @RequestParam(required = true) String cepOrigin,
            @RequestParam(required = true) String cepDestination,
            @RequestParam(required = true) double weight
    ) {
        ViaCepUtils.validateCep(cepDestination);
        try {
            Cep cep = searchForCepInformation(cepDestination);

            Shipping shipping = calculateShipping(cepOrigin, cepDestination, weight);

            logger.info("Dados do CEP de origem: " + searchForCepInformation(cepOrigin));
            logger.info("Dados do CEP de destino: " + cep.toString());
            logger.info("Dados do Frete: " + shipping);

            return new ResponseEntity<>(cep, HttpStatus.OK);
        } catch (ViaCepException | ViaCepFormatException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private static Cep searchForCepInformation(String cep) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.of(1, MINUTES))
                    .build();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(viaCepUrl + cep + "/json")).timeout(Duration.of(1, MINUTES))
                    .build();

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            logger.info("Status Code: " + response.statusCode());

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), Cep.class);
        } catch (IOException | InterruptedException | ViaCepException | ViaCepFormatException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static Shipping calculateShipping(String cepOrigin, String cepDestiny, double weight) {
        ViaCepUtils.validateCep(cepOrigin);
        ViaCepUtils.validateCep(cepDestiny);

        Cep originAddress = searchForCepInformation(cepOrigin);
        Cep destinationAddress = searchForCepInformation(cepDestiny);

        String dddOrigin = originAddress.getDdd();
        String dddDestiny = destinationAddress.getDdd();
        String originState = originAddress.getUf();
        String destinationState = destinationAddress.getUf();

        int deliveryInDays;
        double shippingValue = weight * 15;

        if (dddOrigin.equals(dddDestiny)) {
            shippingValue *= 0.50;
            deliveryInDays = 1;
        } else if (originState.equals(destinationState)) {
            shippingValue *= 0.75;
            deliveryInDays = 3;
        } else {
            deliveryInDays = 10;
        }

        return new Shipping(cepOrigin, cepDestiny, shippingValue, deliveryInDays);
    }
}
