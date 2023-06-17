package com.alga.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.alga.algafood.domain.exception.EntidadeEmUsoException;
import com.alga.algafood.domain.exception.EntidadeNaoEncontradaException;
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
		return restauranteRepository.findAll();
	}
	
	public Restaurante buscar(Long retauranteId){
		return restauranteRepository.findById(retauranteId).orElseGet(null);
	}
	
	public Restaurante salvar(Long restauranteId, Restaurante restaurante) {
		Optional<Restaurante> restauranteColeta = restauranteRepository.findById(restauranteId);
		
		if(restauranteColeta.isEmpty()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe um cadastro de Restaurante com o código %d", restauranteId));
		}
		
		// para assumir todas as propriedades
		BeanUtils.copyProperties(restaurante, restauranteColeta.get(), "id");
		
		return restauranteRepository.save(restauranteColeta.get());
	}
	
	
	public Restaurante adicionar(Restaurante restaurante) {
		
		return restauranteRepository.save(restaurante);
	}
	
	public void excluir(Long restauranteId) {

		Optional<Restaurante> restauranteColeta = restauranteRepository.findById(restauranteId);

		try {
			restauranteRepository.deleteById(restauranteColeta.get().getId());

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe um cadastro de restaurante com o código %d", restauranteId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Restaurante de código %d não pode ser removida, está em uso", restauranteId));
		}

	}
}
