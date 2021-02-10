package com.hibicode.beerstore.service;

import static com.hibicode.beerstore.model.BeerType.LAGER;
import static com.hibicode.beerstore.service.mother.BeerObjectMother.construirBeer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import com.hibicode.beerstore.repository.Beers;
import com.hibicode.beerstore.service.exception.CervejaJaExisteException;
import com.hibicode.beerstore.service.exception.CervejaNaoExisteException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;


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
    public void configurandoInicializacao() {
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
        Beer beerNoBanco = construirBeer();
        when(beersMocked.findByNomeAndBeerType("Heineken", BeerType.PILSEN)).thenReturn(Optional.of(beerNoBanco));
        /* Fim construção do objeto mock */

        Beer beerRepetida = beerNoBanco;
        beerService.salvar(beerRepetida);
    }

    /**
     * Teste de inserção de nova beer
     */
    @Test
    public void deve_salvar_nova_cerveja() {
        Beer novaBeer = construirBeer();
        beerService.salvar(novaBeer);
    }

    @Test(expected = CervejaNaoExisteException.class)
    public void deve_bloquear_atualizacao_de_cerveja_que_nao_existe() {
        when(beersMocked.findById(anyLong())).thenReturn(Optional.empty());
        beerService.atualizar(construirBeer());
        verify(beersMocked).findById(anyLong());
        verifyNoMoreInteractions(beersMocked);
    }

    @Test
    public void deve_atualizar_cerveja_com_sucesso() {
        Beer beer = construirBeer();
        when(beersMocked.findById(beer.getId())).thenReturn(Optional.of(beer));

        Beer beerRequest = construirBeer(1L,"Vinho", LAGER, BigDecimal.TEN);
        beerService.atualizar(beerRequest);

        verify(beersMocked).findById(anyLong());
        ArgumentCaptor<Beer> acBeer = ArgumentCaptor.forClass(Beer.class);
        verify(beersMocked).save(acBeer.capture());
        assertThat(acBeer.getValue(), samePropertyValuesAs(beerRequest));
    }

    @Test(expected = CervejaNaoExisteException.class)
    public void deve_bloquear_remocao_de_cerveja_que_nao_existe() {
        when(beersMocked.findById(anyLong())).thenReturn(Optional.empty());
        Long idCervejaInexistente = 700L;

        beerService.deletar(idCervejaInexistente);
        verify(beersMocked).findById(anyLong());
        verifyNoMoreInteractions(beersMocked);
    }

    @Test
    public void deve_deletar_cerveja_com_sucesso() {
        Beer beerDataBase = construirBeer(1L,"Vinho", LAGER, BigDecimal.TEN);
        when(beersMocked.findById(beerDataBase.getId())).thenReturn(Optional.of(beerDataBase));

        beerService.deletar(beerDataBase.getId());

        verify(beersMocked).findById(anyLong());
        ArgumentCaptor<Long> acId = ArgumentCaptor.forClass(Long.class);
        verify(beersMocked).deleteById(acId.capture());
        assertEquals(beerDataBase.getId(), acId.getValue());
    }
}
