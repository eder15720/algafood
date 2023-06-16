package com.alga.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.alga.algafood.AlgafoodApiAgoravaiApplication;
import com.alga.algafood.domain.model.Cozinha;
import com.alga.algafood.domain.model.Restaurante;
import com.alga.algafood.domain.repository.CozinhaRepository;
import com.alga.algafood.domain.repository.RestauranteRepository;

public class BuscaRestauranteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiAgoravaiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
		
		Restaurante restaurante = restauranteRepository.buscar(1L);
		
		System.out.println(restaurante.getNome());
	}

}
