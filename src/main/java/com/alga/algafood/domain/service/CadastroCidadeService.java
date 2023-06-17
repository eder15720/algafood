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
import com.alga.algafood.domain.model.Cidade;
import com.alga.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	public List<Cidade> listar(){
		return cidadeRepository.findAll();
	}
	
	public Cidade buscar(Long cidadeId){
		return cidadeRepository.findById(cidadeId).orElse(null);
	}
	
	public Cidade salvar(Long cidadeId, Cidade cidade) {
		
		Optional<Cidade> cidadeColeta = cidadeRepository.findById(cidadeId);
		
		// para assumir todas as propriedades
		if(cidadeColeta.isPresent()) {
			BeanUtils.copyProperties(cidade, cidadeColeta.get(), "id");
			
			return cidadeRepository.save(cidadeColeta.get());	
		}
		
		throw new EntidadeNaoEncontradaException("Erro ao encontrar a cidade para atualização.");
		
	}
	
	public Cidade adicionar(Cidade cidade) {
		
		return cidadeRepository.save(cidade);
	}
	
	public void excluir(Long cidadeId) {

		Optional<Cidade> cidadeColeta = cidadeRepository.findById(cidadeId);

		try {
			cidadeRepository.deleteById(cidadeColeta.get().getId());

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe um cadastro de restaurante com o código %d", cidadeId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Restaurante de código %d não pode ser removida, está em uso", cidadeId));
		}

	}
}
