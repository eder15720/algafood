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
import com.alga.algafood.domain.model.Permissao;
import com.alga.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {
	
	@Autowired
	PermissaoRepository permissaoRepository;

	public List<Permissao> listar(){
		return permissaoRepository.findAll();
	}
	
	public Permissao buscar(Long estadoId){
		return permissaoRepository.findById(estadoId).orElse(null);
	}
	
	public Permissao salvar(Long estadoId, Permissao estado) {
		Optional<Permissao> estadoColeta = permissaoRepository.findById(estadoId);
		
		if(estadoColeta.isEmpty()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe um cadastro de Permissao com o código %d", estadoId));
		}
		
		// para assumir todas as propriedades
		BeanUtils.copyProperties(estado, estadoColeta.get(), "id");
		
		return permissaoRepository.save(estadoColeta.get());
	}
	
	
	public Permissao adicionar(Permissao estado) {
		
		return permissaoRepository.save(estado);
	}
	
	public void excluir(Long estadoId) {

		Optional<Permissao> estadoColeta = permissaoRepository.findById(estadoId);

		try {
			permissaoRepository.deleteById(estadoColeta.get().getId());

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe um cadastro de estado com o código %d", estadoId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Permissao de código %d não pode ser removida, está em uso", estadoId));
		}

	}
}
