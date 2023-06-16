package com.alga.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.alga.algafood.AlgafoodApiAgoravaiApplication;
import com.alga.algafood.domain.model.Restaurante;
import com.alga.algafood.domain.repository.RestauranteRepository;

public class AlteracaoRestauranteMain {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiAgoravaiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);	
		
		Restaurante restaurante = new Restaurante();
		restaurante.setId(1L);
		restaurante.setNome("Brasileira atualizada");
		
		restauranteRepository.salvar(restaurante);		

	}

}
