package com.alga.algafood.infraestructure.repository;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.alga.algafood.domain.model.Cidade;
import com.alga.algafood.domain.model.Estado;


@Component
public class CidadeRepositoryImpl {
	
	@PersistenceContext
	private EntityManager manager;

	public List<Cidade> listar(){
		TypedQuery<Cidade> query = manager.createQuery("from Cidade", Cidade.class);
		
		return query.getResultList();
	}
	
	public Cidade buscar(Long id) {
		return manager.find(Cidade.class, id);
	}
	
	@Transactional
	public Cidade salvar(Cidade cidade) {
		return manager.merge(cidade);
	}
	
	@Transactional
	public void remover(Cidade cidade) {
		cidade = buscar(cidade.getId());
		manager.remove(cidade);
	}
}
