package com.teste.permutador.adapter.input.rest.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestFieldError {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String field;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Object value;
}
