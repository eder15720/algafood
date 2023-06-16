package com.alga.algafood.infraestructure.repository;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.alga.algafood.domain.model.Cozinha;
import com.alga.algafood.domain.model.Restaurante;


@Component
public class RestauranteRepositoryImpl {
	
	@PersistenceContext
	private EntityManager manager;

	public List<Restaurante> listar(){
		TypedQuery<Restaurante> query = manager.createQuery("from Restaurante", Restaurante.class);
		
		return query.getResultList();
	}
	
	public Restaurante buscar(Long id) {
		return manager.find(Restaurante.class, id);
	}
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		return manager.merge(restaurante);
	}
	
	@Transactional
	public void remover(Restaurante restaurante) {
		restaurante = buscar(restaurante.getId());
		manager.remove(restaurante);
	}
}
