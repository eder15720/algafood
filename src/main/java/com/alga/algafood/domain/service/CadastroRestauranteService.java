package com.alga.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.alga.algafood.domain.exception.EntidadeEmUsoException;
import com.alga.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.alga.algafood.domain.model.Cozinha;
import com.alga.algafood.domain.model.Restaurante;
import com.alga.algafood.domain.repository.CozinhaRepository;
import com.alga.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	RestauranteRepository restauranteRepository;
	
	@Autowired
	CozinhaRepository cozinhaRepository;

	public List<Restaurante> listar(){
		return restauranteRepository.listar();
	}
	
	public Restaurante buscar(Long retauranteId){
		return restauranteRepository.buscar(retauranteId);
	}
	
	public Restaurante salvar(Long restauranteId, Restaurante restaurante) {
		Restaurante restauranteColeta = restauranteRepository.buscar(restauranteId);
		
		if(restauranteColeta == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe um cadastro de Restaurante com o código %d", restauranteId));
		}
		
		// para assumir todas as propriedades
		BeanUtils.copyProperties(restaurante, restauranteColeta, "id");
		
		return restauranteRepository.salvar(restauranteColeta);
	}
	
	
	public Restaurante adicionar(Restaurante restaurante) {
		
		return restauranteRepository.salvar(restaurante);
	}
	
	public void excluir(Long restauranteId) {

		Restaurante restauranteAtual = restauranteRepository.buscar(restauranteId);

		try {
			restauranteRepository.remover(restauranteAtual);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe um cadastro de restaurante com o código %d", restauranteId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Restaurante de código %d não pode ser removida, está em uso", restauranteId));
		}

	}
}
