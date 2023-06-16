package com.alga.algafood.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.alga.algafood.domain.model.Restaurante;

public interface RestauranteRepository extends CrudRepository<Restaurante, Long>{
	
	List<Restaurante> listar();
	Restaurante buscar(Long id);
	Restaurante salvar(Restaurante restaurante);
	void remover(Restaurante restaurante);

}
