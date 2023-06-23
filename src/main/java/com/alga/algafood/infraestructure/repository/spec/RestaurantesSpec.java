package com.alga.algafood.infraestructure.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import com.alga.algafood.domain.model.Restaurante;

public class RestaurantesSpec {
	
	public static Specification<Restaurante> comFreteGratis(){
		return new RestauranteComFreteGratisSpec();
	}
	
	public static Specification<Restaurante> comNomeSemelhante(String nome){
		return new RestauranteComNomeSemelhanteSpec(nome);
	}

}
