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
import com.alga.algafood.domain.model.FormaPagamento;
import com.alga.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagtoService {
	
	@Autowired
	FormaPagamentoRepository formaPagamentoRepository;

	public List<FormaPagamento> listar(){
		return formaPagamentoRepository.findAll();
	}
	
	public FormaPagamento buscar(Long formaPagamentoId){
		return formaPagamentoRepository.findById(formaPagamentoId).orElse(null);
	}
	
	public FormaPagamento salvar(Long formaPagamentoId, FormaPagamento formaPagamento) {
		Optional<FormaPagamento> formaPagamentoColeta = formaPagamentoRepository.findById(formaPagamentoId);
		
		if(formaPagamentoColeta == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe um cadastro de FormaPagamento com o código %d", formaPagamentoId));
		}
		
		// para assumir todas as propriedades
		BeanUtils.copyProperties(formaPagamento, formaPagamentoColeta.get(), "id");
		
		return formaPagamentoRepository.save(formaPagamentoColeta.get());
	}
	
	
	public FormaPagamento adicionar(FormaPagamento formaPagamento) {
		
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	public void excluir(Long formaPagamentoId) {

		Optional<FormaPagamento> formaPagamentoColeta = formaPagamentoRepository.findById(formaPagamentoId);

		try {
			formaPagamentoRepository.deleteById(formaPagamentoColeta.get().getId());

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao existe um cadastro de formaPagamento com o código %d", formaPagamentoId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("FormaPagamento de código %d não pode ser removida, está em uso", formaPagamentoId));
		}
	}
}
