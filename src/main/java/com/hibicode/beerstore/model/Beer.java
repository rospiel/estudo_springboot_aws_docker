package com.hibicode.beerstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Modelo de entidade de beer
 * @author Rodrigo
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


    private String nome;
    private BeerType beerType;
    private BigDecimal volume;
}
