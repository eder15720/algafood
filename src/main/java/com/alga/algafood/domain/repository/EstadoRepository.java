package com.alga.algafood.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alga.algafood.domain.model.Estado;

@Repository
public interface EstadoRepository extends CrudRepository<Estado, Long> {
	
	List<Estado> listar();
	Estado buscar(Long id);
	Estado salvar(Estado estado);
	void remover(Estado estado);

}
