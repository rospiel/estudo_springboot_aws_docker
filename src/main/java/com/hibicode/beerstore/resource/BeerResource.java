package com.hibicode.beerstore.resource;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.repository.Beers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<String> all() {
        return Arrays.asList("Heineken", "Colorado Indiga", "Stella Artois");
    }

    /**
     * Recebe beer no formato json convertida conforme anotação @RequestBody e cadastra retornando o objeto salvo
     * @param beer
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Beer create(@RequestBody Beer beer) {
        return beers.save(beer);
    }
}
