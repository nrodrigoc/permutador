package com.teste.permutador.application.port.in;

import com.teste.permutador.adapter.input.rest.in.dto.StringPermutavelRequest;

import java.util.List;

public interface PermutacaoUseCase {

    List<String> realizaPermuta(StringPermutavelRequest stringPermutavelRequest);
}
