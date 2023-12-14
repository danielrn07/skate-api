package com.example.assessmentjavaapi;

import com.example.assessmentjavaapi.exception.ViaCepException;
import com.example.assessmentjavaapi.exception.ViaCepFormatException;
import com.example.assessmentjavaapi.utils.ViaCepUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ViaCepUtilTest {
    @Test
    @DisplayName("Verifica se uma ViaCepException é lançada para um CEP")
    public void TestInvalidCep() {
        // Verifica se uma ViaCepException é lançada para um CEP nulo
        assertThrows(ViaCepException.class, () -> {
            ViaCepUtils.validateCep(null);
        });

        // Verifica se uma ViaCepException é lançada para um CEP vazio ou em branco
        assertThrows(ViaCepException.class, () -> {
            ViaCepUtils.validateCep("");
        });

        // Verifica se uma ViaCepException é lançada para um CEP com menos de 8 caracteres
        assertThrows(ViaCepException.class, () -> {
            ViaCepUtils.validateCep("1234567");
        });

        // Verifica se uma ViaCepFormatException é lançada para um CEP com mais de 8 caracteres
        assertThrows(ViaCepFormatException.class, () -> {
            ViaCepUtils.validateCep("123456789");
        });
    }
}
