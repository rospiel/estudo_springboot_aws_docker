package com.hibicode.beerstore.repository;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface de Beer
 * Fazendo uso de jparepository afim de reaproceitar inúmeros métodos prontos
 */
public interface Beers extends JpaRepository<Beer, Long> {

    /**
     * Encontra Beer de mesmo nome e tipo na base
     * @param nome
     * @param beerType
     * @return
     */
    Optional<Beer> findByNomeAndBeerType(String nome, BeerType beerType);
}
