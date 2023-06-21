package com.alga.algafood.domain;

import java.math.BigDecimal;
import java.util.List;

import com.alga.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> consultar(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

}