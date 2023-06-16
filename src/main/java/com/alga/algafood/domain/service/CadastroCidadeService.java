package com.alga.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.alga.algafood.domain.exception.EntidadeEmUsoException;
import com.alga.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.alga.algafood.domain.model.Cidade;
import com.alga.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	public List<Cidade> listar(){
		return cidadeRepository.listar();
	}
	
	public Cidade buscar(Long cidadeId){
		return cidadeRepository.buscar(cidadeId);
	}
	
	public Cidade salvar(Long cidadeId, Cidade cidade) {
		Cidade cidadeColeta = cidadeRepository.buscar(cidadeId);
		
		if(cidadeColeta == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe um cadastro de cidade com o código %d", cidadeId));
		}
		
		// para assumir todas as propriedades
		BeanUtils.copyProperties(cidade, cidadeColeta, "id");
		
		return cidadeRepository.salvar(cidadeColeta);
	}
	
	public Cidade adicionar(Cidade cidade) {
		
		return cidadeRepository.salvar(cidade);
	}
	
	public void excluir(Long cidadeId) {

		Cidade cidadeAtual = cidadeRepository.buscar(cidadeId);

		try {
			cidadeRepository.remover(cidadeAtual);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe um cadastro de restaurante com o código %d", cidadeId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Restaurante de código %d não pode ser removida, está em uso", cidadeId));
		}

	}
}
