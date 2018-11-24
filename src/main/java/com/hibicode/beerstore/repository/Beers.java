package com.hibicode.beerstore.repository;

import com.hibicode.beerstore.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de Beer
 * Fazendo uso de jparepository afim de reaproceitar inúmeros métodos prontos
 */
public interface Beers extends JpaRepository<Beer, Long> {

}
