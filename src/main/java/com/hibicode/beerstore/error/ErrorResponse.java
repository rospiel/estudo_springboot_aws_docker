package com.hibicode.beerstore.error;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import static lombok.AccessLevel.PRIVATE;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.Collections;
import java.util.List;

/**
 * Classe para tratamento de erros
 * @author Rodrigo
 */
@JsonAutoDetect(fieldVisibility = ANY) /* Permite a conversão mesmo em atributos privados - PACOTE ESTÁTICO */
@RequiredArgsConstructor(access = PRIVATE) /* Criação do construtor automaticamente - PACOTE ESTÁTICO */
public class ErrorResponse {

    private final int statusCode;
    private final List<ApiError> errors;

    /**
     * Construtor para lista de erros
     * @param httpStatus
     * @param errors
     * @return
     */
    static ErrorResponse of(HttpStatus httpStatus, List<ApiError> errors) {
        return new ErrorResponse(httpStatus.value(), errors);
    }

    /**
     * Construtor para somente um erro
     * @param httpStatus
     * @param error
     * @return
     */
    static ErrorResponse of(HttpStatus httpStatus, ApiError error) {
        return of(httpStatus, Collections.singletonList(error));
    }

    /**
     * Classe criada pois é usada somente neste caso
     * @author Rodrigo
     */
    @JsonAutoDetect(fieldVisibility = ANY)
    @RequiredArgsConstructor
    static class ApiError {
        private final String code;
        private final String message;
    }
}
