package com.hibicode.beerstore.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Classe genérica para exceções de negócio
 */
@RequiredArgsConstructor
@Getter
public class BusinessException extends RuntimeException {

    private final String code;
    private final HttpStatus status;

}
