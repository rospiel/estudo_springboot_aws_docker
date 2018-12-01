package com.hibicode.beerstore.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import com.hibicode.beerstore.repository.Beers;
import com.hibicode.beerstore.service.exception.CervejaJaExisteException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.math.BigDecimal;
import java.util.Optional;

/**
 * Classe de teste de Beer
 */
public class BeerServiceTest {

    private BeerService beerService;

    @Mock
    private Beers beersMocked;

    /**
     * Método de inicialização para qualquer teste realizado
     */
    @Before
    public void configuracaoInicializacao() {
        /* Inicializa os objetos do tipo mock */
        MockitoAnnotations.initMocks(this);
        beerService = new BeerService(beersMocked);
    }

    /**
     * Teste que valida se a cerveja já existe na base impossibilitando duplicidade
     */
    @Test(expected = CervejaJaExisteException.class)
    public void deve_bloquear_criacao_de_cerveja_que_existe() {
        /* Construção do objeto mock */
        Beer beerNoBanco = new Beer();
        beerNoBanco.setId(10L);
        beerNoBanco.setNome("Heineken");
        beerNoBanco.setBeerType(BeerType.PILSEN);
        beerNoBanco.setVolume(new BigDecimal("500"));
        when(beersMocked.findByNomeAndBeerType("Heineken", BeerType.PILSEN)).thenReturn(Optional.of(beerNoBanco));
        /* Fim construção do objeto mock */

        Beer novaBeer = new Beer();
        novaBeer.setNome("Heineken");
        novaBeer.setBeerType(BeerType.PILSEN);
        novaBeer.setVolume(new BigDecimal("500"));

        beerService.salvar(novaBeer);
    }

    /**
     * Teste de inserção de nova beer
     */
    @Test
    public void deve_salvar_nova_cerveja() {
        Beer novaBeer = new Beer();
        novaBeer.setNome("Heineken");
        novaBeer.setBeerType(BeerType.PILSEN);
        novaBeer.setVolume(new BigDecimal("500"));
        Beer beerSaved = beerService.salvar(novaBeer);

        /* Construção do objeto mock */
        Beer novaBeerBancoDados = new Beer();
        novaBeerBancoDados.setId(10L);
        novaBeerBancoDados.setNome("Heineken");
        novaBeerBancoDados.setBeerType(BeerType.PILSEN);
        novaBeerBancoDados.setVolume(new BigDecimal("500"));
        when(beersMocked.save(novaBeer)).thenReturn(novaBeerBancoDados);
        /* Fim Construção do objeto mock */

        /* Fazendo uso de hamcrest */
        assertThat(beerSaved.getId(), equalTo(10L));
        assertThat(beerSaved.getNome(), equalTo("Heineken"));
        assertThat(beerSaved.getBeerType(), equalTo(BeerType.PILSEN));
    }
}
