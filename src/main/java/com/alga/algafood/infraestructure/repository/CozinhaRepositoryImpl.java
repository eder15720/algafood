package com.alga.algafood.infraestructure.repository;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Repository;

import com.alga.algafood.domain.model.Cozinha;
import com.alga.algafood.domain.repository.CozinhaRepository;


@Repository
public abstract class CozinhaRepositoryImpl implements CozinhaRepository{
	
	@PersistenceContext
	private EntityManager manager;

	public List<Cozinha> listar(){
		TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class);
		
		return query.getResultList();
	}
	
	public Cozinha buscar(Long id) {
		return manager.find(Cozinha.class, id);
	}
	
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}
	
	@Transactional
	public void remover(Long id) {
		Cozinha cozinha = buscar(id);
		
		if(cozinha == null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(cozinha);
	}

	public List<Cozinha> consultarPorNome(String nome) {
		return manager.createQuery("from Cozinha where nome = :nome", Cozinha.class)
				.setParameter("nome", nome)
				.getResultList();
	}

}
