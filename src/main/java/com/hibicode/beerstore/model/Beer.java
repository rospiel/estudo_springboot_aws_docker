package com.hibicode.beerstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Modelo de entidade de beer
 * @author Rodrigo
 * As mensagens estão sendo buscadas no api_errors.properties
 */

@Entity
@Data /* Gera os gets, sets, equals e hashcode automaticamente */
@EqualsAndHashCode(onlyExplicitlyIncluded = true) /* Indicamos que será gerado conforme os atributos que selecionarmos */
public class Beer {

    @Id
    @SequenceGenerator(name = "beer_seq", sequenceName = "beer_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beer_seq")
    @EqualsAndHashCode.Include /* Pedindo pro lombok para gerar com este atributo */
    private Long id;

    @NotBlank(message = "beers-1")
    private String nome;

    @NotNull(message = "beers-2")
    private BeerType beerType;

    @NotNull(message = "beers-3")
    @DecimalMin(value = "0", message = "beers-4")
    private BigDecimal volume;
}
