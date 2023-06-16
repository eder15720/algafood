package com.alga.algafood.infraestructure.repository;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.alga.algafood.domain.model.FormaPagamento;
import com.alga.algafood.domain.model.Restaurante;


@Component
public class FormaPagtoRepositoryImpl {
	
	@PersistenceContext
	private EntityManager manager;

	public List<FormaPagamento> listar(){
		TypedQuery<FormaPagamento> query = manager.createQuery("from FormaPagamento", FormaPagamento.class);
		
		return query.getResultList();
	}
	
	public FormaPagamento buscar(Long id) {
		return manager.find(FormaPagamento.class, id);
	}
	
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return manager.merge(formaPagamento);
	}
	
	@Transactional
	public void remover(FormaPagamento formaPagamento) {
		formaPagamento = buscar(formaPagamento.getId());
		manager.remove(formaPagamento);
	}
}
