package com.teste.permutador.adapter.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.permutador.adapter.input.rest.in.dto.StringPermutavelRequest;
import com.teste.permutador.application.port.in.PermutacaoUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PermutacaoController.class)
class PermutacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PermutacaoUseCase permutacaoUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar 204 No Content quando requisição for válida")
    void deveRetornarNoContentQuandoRequisicaoValida() throws Exception {
        // given
        StringPermutavelRequest request = new StringPermutavelRequest("abc");

        // when/then
        mockMvc.perform(post("/api/permutacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(permutacaoUseCase).realizaPermuta(request);
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request quando stringPermutavel for nula ou vazia")
    void deveRetornarBadRequestQuandoStringInvalida() throws Exception {
        // Dado um JSON com campo inválido (aqui usando string vazia como exemplo)
        String json = """
                {
                    "stringPermutavel": ""
                }
                """;

        mockMvc.perform(post("/api/permutacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 com mensagem de erro de validação para string inválida")
    void deveRetornarErroValidacaoParaStringInvalida() throws Exception {
        // Dado
        StringPermutavelRequest request = new StringPermutavelRequest("abc1");

        // Quando & Então
        mockMvc.perform(post("/api/permutacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("Requisição em formato inválido."))
                .andExpect(jsonPath("$.path").value("/api/permutacao"))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("stringPermutavel"))
                .andExpect(jsonPath("$.fieldErrors[0].message").value("Deve conter apenas letras."))
                .andExpect(jsonPath("$.fieldErrors[0].value").value("abc1"));
    }

    @Test
    @DisplayName("Deve retornar erro de validação quando houver letras repetidas")
    void deveFalharComLetrasRepetidas() throws Exception {
        // Arrange
        StringPermutavelRequest request = new StringPermutavelRequest("abbc");

        // Act & Assert
        mockMvc.perform(post("/api/permutacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("Requisição em formato inválido."))
                .andExpect(jsonPath("$.path").value("/api/permutacao"))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("stringPermutavel"))
                .andExpect(jsonPath("$.fieldErrors[0].message").value("O campo 'stringPermutavel' não deve conter letras repetidas."))
                .andExpect(jsonPath("$.fieldErrors[0].value").value("abbc"));
    }

    @Test
    @DisplayName("Deve retornar erro de validação quando stringPermutavel excede o tamanho permitido")
    void deveFalharComTamanhoExcedido() throws Exception {
        // Arrange
        StringPermutavelRequest request = new StringPermutavelRequest("abcdefghijklm"); // 13 letras

        // Act & Assert
        mockMvc.perform(post("/api/permutacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("Requisição em formato inválido."))
                .andExpect(jsonPath("$.path").value("/api/permutacao"))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("stringPermutavel"))
                .andExpect(jsonPath("$.fieldErrors[0].message")
                        .value("O campo stringPermutavel deve ter entre 3 e 10 caracteres."))
                .andExpect(jsonPath("$.fieldErrors[0].value").value("abcdefghijklm"));
    }
}