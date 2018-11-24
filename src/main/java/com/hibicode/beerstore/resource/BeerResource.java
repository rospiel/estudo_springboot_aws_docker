package com.hibicode.beerstore.resource;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.repository.Beers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * Classe dos recursos de beer
 * @author Rodrigo
 */

@RestController
@RequestMapping("/beers")
public class BeerResource {

    @Autowired
    private Beers beers;

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
    @ResponseStatus(HttpStatus.CREATED)
    public Beer create(@Valid @RequestBody Beer beer) {
        return beers.save(beer);
    }
}
