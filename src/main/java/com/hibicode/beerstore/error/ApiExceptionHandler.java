package com.hibicode.beerstore.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.hibicode.beerstore.service.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.hibicode.beerstore.error.ErrorResponse.ApiError;

import java.util.List;
import java.util.Locale;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 * Classe de interceptação de exceções
 * @author Rodrigo
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private static final String NENHUMA_MENSAGEM_DISPONIVEL = "Nenhuma mensagem disponível";
    private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);
    private final MessageSource apiErrorMessageSource;

    /**
     * Método que capta MethodArgumentNotValidException
     * @param exception
     * @param locale
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleNotValidException(MethodArgumentNotValidException exception, Locale locale) {
        Stream<ObjectError> errors = exception.getBindingResult().getAllErrors().stream();
        List<ApiError> apiErrors = errors.map(ObjectError::getDefaultMessage)
                                         .map(code -> toApiError(code, locale))
                                         .collect(toList());

        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Trata passagem de parâmetros incorretos no envio da requisição
     * @param exception
     * @param locale
     * @return
     */
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException exception, Locale locale) {
        final String errorCode = "generic-1";
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale, exception.getValue()));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Método que trata as exceções de negócio
     * @param exception
     * @param locale
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception, Locale locale) {
        final String errorCode = exception.getCode();
        final HttpStatus status = exception.getStatus();
        final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception, Locale locale) {
        LOG.error("Erro inesperado.", exception);
        final String errorCode = "error-1";
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale));
        return ResponseEntity.status(status).body(errorResponse);
    }

    /**
     * Método de construção da ApiError
     * @param code
     * @param locale
     * @param valor
     * @return
     */
    private ApiError toApiError(String code, Locale locale, Object... valor) {
        String message;
        try {
            message = apiErrorMessageSource.getMessage(code, valor, locale);
        } catch (NoSuchMessageException e) {
            LOG.error("Nenhuma mensagem foi identificada para {} código {} localização", code, locale);
            message = NENHUMA_MENSAGEM_DISPONIVEL;
        }

        return new ApiError(code, message);

    }
}
