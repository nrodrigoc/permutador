package com.teste.permutador.common.annotation;

import com.teste.permutador.common.validator.LetrasDistintasValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = LetrasDistintasValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface LetrasDistintas {

    String message() default "Não deve conter letras repetidas.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
