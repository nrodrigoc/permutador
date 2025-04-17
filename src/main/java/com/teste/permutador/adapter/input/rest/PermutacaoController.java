package com.teste.permutador.adapter.input.rest;

import com.teste.permutador.adapter.input.rest.in.dto.StringPermutavelRequest;
import com.teste.permutador.application.port.in.PermutacaoUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/permutacao")
@Validated
public class PermutacaoController {

    private final PermutacaoUseCase permutacaoUseCase;

    @PostMapping
    public ResponseEntity<Void> realizaPermuta(@RequestBody @Valid StringPermutavelRequest request) {
        permutacaoUseCase.realizaPermuta(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
