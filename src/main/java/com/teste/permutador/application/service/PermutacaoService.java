package com.teste.permutador.application.service;


import com.teste.permutador.adapter.input.rest.in.dto.StringPermutavelRequest;
import com.teste.permutador.application.port.in.PermutacaoUseCase;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermutacaoService implements PermutacaoUseCase {

    // Algoritmo Heap para "organizar" o array
    @Override
    public List<String> realizaPermuta(@Valid StringPermutavelRequest stringPermutavelRequest) {
        String stringPermutavel = stringPermutavelRequest.getStringPermutavel();

        char[] array = stringPermutavel.toCharArray();
        int n = array.length;

        List<String> resultado = new ArrayList<>();
        realizaPermuta(array, n, resultado);

        return resultado;
    }

    public void realizaPermuta(char[] array, int n, List<String> resultado) {
        if (n == 1) { // Imprime caso todos os chars tenham sido permutados
            String output = String.valueOf(array);
            System.out.println(output);
            resultado.add(output);
            return;
        }

        for (int i = 0; i < n; i++) { // Realiza a permuta índice a índice
            realizaPermuta(array, n - 1, resultado);

            // Troca as posições entre o primeiro índice (ou i, caso n seja par) e o índice n
            if (n % 2 == 0) {
                trocaPosicoesIndices(array, i, n - 1);
            } else {
                trocaPosicoesIndices(array, 0, n - 1);
            }
        }
    }

    private void trocaPosicoesIndices(char[] array, int i, int j) {
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
