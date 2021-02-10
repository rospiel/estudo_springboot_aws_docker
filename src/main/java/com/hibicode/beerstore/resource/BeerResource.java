package com.hibicode.beerstore.resource;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.repository.Beers;
import com.hibicode.beerstore.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * Classe dos recursos de beer
 * @author Rodrigo
 */

@RestController
@RequestMapping("/beers")
public class BeerResource {

    @Autowired
    private Beers beers;

    @Autowired
    private BeerService beerService;

    /**
     * Lista todas as beers
     * @return
     */
    @GetMapping
    public List<Beer> all() {
        return beers.findAll();
    }

    /**
     * Recebe beer no formato json convertida conforme anotação @RequestBody e cadastra retornando o objeto salvo
     * @param beer
     * @return
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public Beer criar(@Valid @RequestBody Beer beer) {
        return beerService.salvar(beer);
    }

    @PutMapping("/{id}")
    public Beer atualizar(@PathVariable Long id, @Valid @RequestBody Beer beer) {
        beer.setId(id);
        return beerService.atualizar(beer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        beerService.deletar(id);
    }
}
