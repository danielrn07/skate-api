package com.example.assessmentjavaapi.utils;

import com.example.assessmentjavaapi.exception.ViaCepException;
import com.example.assessmentjavaapi.exception.ViaCepFormatException;
import com.example.assessmentjavaapi.service.ViaCepService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ViaCepUtils {
    public static void validateCep(String cep) {
        if (Objects.isNull(cep) || cep.isEmpty() || cep.isBlank())
            throw new ViaCepException("O cep informado não pode ser nulo ou vazio");
        if (cep.length() > 8) throw new ViaCepFormatException("CEP fora do formato");
        if (cep.length() < 8) throw new ViaCepException("CEP faltando numeros");
    }
}
