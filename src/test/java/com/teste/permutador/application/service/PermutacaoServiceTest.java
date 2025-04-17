package com.teste.permutador.application.service;

import com.teste.permutador.adapter.input.rest.in.dto.StringPermutavelRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PermutacaoServiceTest {

    private PermutacaoService service;

    @BeforeEach
    void setUp() {
        service = new PermutacaoService();
    }

    @Test
    void deveGerarPermutacoesParaAB() {
        // given
        StringPermutavelRequest request = new StringPermutavelRequest("ab");

        // when
        List<String> resultado = service.realizaPermuta(request);

        // then
        assertThat(resultado).containsExactlyInAnyOrder("ab", "ba");
        assertThat(resultado).hasSize(2);
    }

    @Test
    void deveGerarPermutacoesParaABC() {
        StringPermutavelRequest request = new StringPermutavelRequest("abc");

        List<String> resultado = service.realizaPermuta(request);

        assertThat(resultado).containsExactlyInAnyOrder(
                "abc", "acb", "bac", "bca", "cab", "cba"
        );
        assertThat(resultado).hasSize(6);
    }

    @Test
    void deveGerarUmaPermutacaoParaUmCaractere() {
        StringPermutavelRequest request = new StringPermutavelRequest("x");

        List<String> resultado = service.realizaPermuta(request);

        assertThat(resultado).containsExactly("x");
        assertThat(resultado).hasSize(1);
    }

    @Test
    void deveGerarListaVaziaParaStringVazia() {
        StringPermutavelRequest request = new StringPermutavelRequest("");

        List<String> resultado = service.realizaPermuta(request);

        assertThat(resultado).isEmpty();
    }
}
