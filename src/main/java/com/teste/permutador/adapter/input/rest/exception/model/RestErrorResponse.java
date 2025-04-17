package com.teste.permutador.adapter.input.rest.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
public class RestErrorResponse {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final ZonedDateTime timestamp;

    private final int statusCode;
    private final String message;
    private final String path;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<RestFieldError> fieldErrors;
}