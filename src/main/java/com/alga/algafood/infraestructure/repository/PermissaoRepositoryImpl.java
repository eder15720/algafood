package com.alga.algafood.infraestructure.repository;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.alga.algafood.domain.model.Cozinha;
import com.alga.algafood.domain.model.Permissao;


@Component
public class PermissaoRepositoryImpl {
	
	@PersistenceContext
	private EntityManager manager;

	public List<Permissao> listar(){
		TypedQuery<Permissao> query = manager.createQuery("from Permissao", Permissao.class);
		
		return query.getResultList();
	}
	
	public Permissao buscar(Long id) {
		return manager.find(Permissao.class, id);
	}
	
	@Transactional
	public Permissao salvar(Permissao permissao) {
		return manager.merge(permissao);
	}
	
	@Transactional
	public void remover(Permissao permissao) {
		permissao = buscar(permissao.getId());
		manager.remove(permissao);
	}
}
