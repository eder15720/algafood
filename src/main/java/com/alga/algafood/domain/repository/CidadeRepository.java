package com.alga.algafood.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alga.algafood.domain.model.Cidade;

@Repository
public interface CidadeRepository extends CrudRepository<Cidade, Long> {
	
	List<Cidade> listar();
	Cidade buscar(Long id);
	Cidade salvar(Cidade cidade);
	void remover(Cidade cidade);

}
