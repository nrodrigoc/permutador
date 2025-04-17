package com.teste.permutador.adapter.input.rest.exception;

import com.teste.permutador.adapter.input.rest.exception.model.RestErrorResponse;
import com.teste.permutador.adapter.input.rest.exception.model.RestFieldError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorResponse> handleBindException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        return buildExceptionForValidationFields(request, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorResponse> handleException(HttpServletRequest request, Exception ex) {
        return new ResponseEntity<>(RestErrorResponse.builder()
                .timestamp(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("UTC")))
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Erro ao processar a requisição.")
                .path(getUrlCompleta(request))
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<RestErrorResponse> buildExceptionForValidationFields(HttpServletRequest request, BindException ex) {
        RestErrorResponse errorResponse = this.buildErrorField(request, "Requisição em formato inválido.");
        List<RestFieldError> errors = new ArrayList<>();

        for (var objectError : ex.getBindingResult().getAllErrors()) {
            if (objectError instanceof FieldError fieldError) {
                var error = this.getFieldError(fieldError);
                errors.add(error);
            }
        }
        errorResponse.setFieldErrors(errors);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ex.printStackTrace();
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    private RestFieldError getFieldError(FieldError fieldError) {
        return RestFieldError.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .value(fieldError.getRejectedValue())
                .build();
    }

    private RestErrorResponse buildErrorField(HttpServletRequest request, String message) {
        return RestErrorResponse.builder()
                .timestamp(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("UTC")))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .path(getUrlCompleta(request))
                .build();
    }

    private String getUrlCompleta(HttpServletRequest request) {
        var requestURI = new StringBuilder(request.getRequestURI());
        var queryString = request.getQueryString();

        if (queryString != null) {
            return requestURI.append('?').append(queryString).toString();
        } else {
            return requestURI.toString();
        }
    }
}
