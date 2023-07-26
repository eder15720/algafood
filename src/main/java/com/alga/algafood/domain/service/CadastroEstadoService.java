package com.alga.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.alga.algafood.domain.exception.EntidadeEmUsoException;
import com.alga.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.alga.algafood.domain.exception.EstadoNaoEncontradoException;
import com.alga.algafood.domain.exception.NegocioException;
import com.alga.algafood.domain.model.Estado;
import com.alga.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String ESTADO_EM_CONFLITO = "Estado de código %d não pode ser removida, está em uso";
	private static final String ESTADO_NAO_ENCONTRADO = "Nao existe um cadastro de Estado com o código %d";
	@Autowired
	EstadoRepository estadoRepository;

	public List<Estado> listar() {
		return estadoRepository.findAll();
	}

	public Estado buscar(Long estadoId) {
		return buscarOuFalhar(estadoId);
	}

	public Estado salvar(Long estadoId, Estado estado) {
		Estado estadoColeta = buscar(estadoId);

		// para assumir todas as propriedades
		BeanUtils.copyProperties(estado, estadoColeta, "id");

		return estadoRepository.save(estadoColeta);
	}

	public Estado adicionar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public void excluir(Long estadoId) {
		Estado estadoColeta = buscar(estadoId);

		try {
			estadoRepository.deleteById(estadoColeta.getId());

		} catch (EstadoNaoEncontradoException e) {
			throw new EntidadeNaoEncontradaException(String.format(ESTADO_NAO_ENCONTRADO, estadoId));
			 
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(ESTADO_EM_CONFLITO, estadoId));
		}

	}

	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
	}
}
