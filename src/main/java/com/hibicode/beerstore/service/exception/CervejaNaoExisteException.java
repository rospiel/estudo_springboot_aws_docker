package com.hibicode.beerstore.service.exception;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

/**
 * Classe de exceção para edição de cervejas que não existem no banco
 * @author Rodrigo
 */
public class CervejaNaoExisteException extends BusinessException {

    public CervejaNaoExisteException() {
        super("beers-6", UNPROCESSABLE_ENTITY);
    }
}
