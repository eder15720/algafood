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
import com.alga.algafood.domain.model.Estado;
import com.alga.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	@Autowired
	EstadoRepository estadoRepository;

	public List<Estado> listar(){
		return estadoRepository.findAll();
	}
	
	public Estado buscar(Long estadoId){
		return estadoRepository.findById(estadoId).orElse(null);
	}
	
	public Estado salvar(Long estadoId, Estado estado) {
		Optional<Estado> estadoColeta = estadoRepository.findById(estadoId);
		
		if(estadoColeta.isEmpty()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe um cadastro de Estado com o código %d", estadoId));
		}
		
		// para assumir todas as propriedades
		BeanUtils.copyProperties(estado, estadoColeta.get(), "id");
		
		return estadoRepository.save(estadoColeta.get());
	}
	
	
	public Estado adicionar(Estado estado) {
		
		return estadoRepository.save(estado);
	}
	
	public void excluir(Long estadoId) {

		Optional<Estado> estadoColeta = estadoRepository.findById(estadoId);

		try {
			estadoRepository.deleteById(estadoColeta.get().getId());

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe um cadastro de estado com o código %d", estadoId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não pode ser removida, está em uso", estadoId));
		}

	}
}
