package com.hibicode.beerstore.service.exception;

import org.springframework.http.HttpStatus;

/**
 * Classe de exceção para cadastro de cervejas que já existem no banco
 * @author Rodrigo
 */
public class CervejaJaExisteException extends BusinessException {

    public CervejaJaExisteException() {
        super("beers-5", HttpStatus.BAD_REQUEST);
    }
}
