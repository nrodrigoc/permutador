package com.teste.permutador.common.validator;

import com.teste.permutador.common.annotation.LetrasDistintas;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

public class LetrasDistintasValidator implements ConstraintValidator<LetrasDistintas, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return true;

        Set<Character> letras = new HashSet<>();
        // Itera sobre cada letra, transforma em minúscula e retorna false caso ela já exista no Set
        for (char c : value.toCharArray()) {
            if (Character.isLetter(c)) {
                char lower = Character.toLowerCase(c);
                if (!letras.add(lower)) {
                    return false;
                }
            }
        }
        return true;
    }
}
