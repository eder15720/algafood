package com.alga.algafood.infraestructure.repository;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.alga.algafood.domain.model.Estado;
import com.alga.algafood.domain.model.Restaurante;


@Component
public class EstadoRepositoryImpl {
	
	@PersistenceContext
	private EntityManager manager;

	public List<Estado> listar(){
		TypedQuery<Estado> query = manager.createQuery("from Estado", Estado.class);
		
		return query.getResultList();
	}
	
	public Estado buscar(Long id) {
		return manager.find(Estado.class, id);
	}
	
	@Transactional
	public Estado salvar(Estado estado) {
		return manager.merge(estado);
	}
	
	@Transactional
	public void remover(Estado estado) {
		estado = buscar(estado.getId());
		manager.remove(estado);
	}
}
