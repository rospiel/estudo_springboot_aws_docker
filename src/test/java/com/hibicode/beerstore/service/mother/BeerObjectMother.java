package com.hibicode.beerstore.service.mother;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

public class BeerObjectMother {

    public static Beer construirBeer() {
        return construirBeer(null,"Heineken", BeerType.PILSEN, new BigDecimal("500"));
    }

    public static Beer construirBeer(Long id, String nome, BeerType beerType, BigDecimal volume) {
        Beer beer = new Beer();
        beer.setId(isNull(id) ? 1L : id);
        beer.setNome(nome);
        beer.setBeerType(beerType);
        beer.setVolume(volume);
        return beer;
    }


}
