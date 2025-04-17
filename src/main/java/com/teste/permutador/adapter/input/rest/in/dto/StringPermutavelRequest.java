package com.teste.permutador.adapter.input.rest.in.dto;

import com.teste.permutador.common.annotation.LetrasDistintas;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StringPermutavelRequest {

    @NotBlank(message = "O campo stringPermutavel é obrigatório.")
    @Size(min = 3, max = 10, message = "O campo stringPermutavel deve ter entre 3 e 10 caracteres.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Deve conter apenas letras.")
    @LetrasDistintas(message = "O campo 'stringPermutavel' não deve conter letras repetidas.")
    private String stringPermutavel;
}
