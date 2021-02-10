package com.hibicode.beerstore.service;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.repository.Beers;
import com.hibicode.beerstore.service.exception.CervejaJaExisteException;
import com.hibicode.beerstore.service.exception.CervejaNaoExisteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Objects.nonNull;

/**
 * Classe de tratativas de negócio de Beer
 * @author Rodrigo
 */
@Service
public class BeerService {

    private Beers beers;

    private Supplier<CervejaNaoExisteException> cervejaNaoExisteException = CervejaNaoExisteException::new;

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

    public Beer atualizar(final Beer beerRequest) {
        Beer beerDataBase = beers.findById(beerRequest.getId()).orElseThrow(cervejaNaoExisteException);
        return beers.save(construirCervejaAtualizada(beerDataBase, beerRequest));
    }

    public void deletar(final Long id) {
        Beer beerDataBase = beers.findById(id).orElseThrow(cervejaNaoExisteException);
        beers.deleteById(id);
    }

    private Beer construirCervejaAtualizada(final Beer beerDataBase, final Beer beerRequest) {
        beerDataBase.setNome(nonNull(beerRequest.getNome()) ? beerRequest.getNome() : beerDataBase.getNome());
        beerDataBase.setBeerType(nonNull(beerRequest.getBeerType()) ? beerRequest.getBeerType() : beerDataBase.getBeerType());
        beerDataBase.setVolume(nonNull(beerRequest.getVolume()) ? beerRequest.getVolume() : beerDataBase.getVolume());
        return beerDataBase;
    }
}
