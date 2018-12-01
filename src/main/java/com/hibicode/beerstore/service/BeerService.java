package com.hibicode.beerstore.service;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.repository.Beers;
import com.hibicode.beerstore.service.exception.CervejaJaExisteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Classe de tratativas de negócio de Beer
 * @author Rodrigo
 */
@Service
public class BeerService {

    private Beers beers;

    public BeerService(@Autowired Beers beers) {
        this.beers = beers;
    }

    /**
     * Salva a Beer no banco desde que já não esteja incluída na base de dados
     * @param beer
     * @return
     */
    public Beer salvar(final Beer beer) {
        Optional<Beer> beerPorNomeETipo = beers.findByNomeAndBeerType(beer.getNome(), beer.getBeerType());

        if(beerPorNomeETipo.isPresent()) {
            throw new CervejaJaExisteException();
        }

        return beers.save(beer);
    }
}
