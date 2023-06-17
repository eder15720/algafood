package com.alga.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alga.algafood.domain.exception.EntidadeEmUsoException;
import com.alga.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.alga.algafood.domain.model.FormaPagamento;
import com.alga.algafood.domain.service.CadastroFormaPagtoService;

@RestController
@RequestMapping("/formaPagtos")
public class FormaPagtoController {

	@Autowired
	CadastroFormaPagtoService cadastroFormaPagtoService;
	
	@GetMapping(produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FormaPagamento>> listar() {
		List<FormaPagamento> formaPagamento = cadastroFormaPagtoService.listar();
		
		return ResponseEntity.status(HttpStatus.OK).body(formaPagamento);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<FormaPagamento> adicionar(@RequestBody FormaPagamento formaPagamento){
		try {	
			formaPagamento = cadastroFormaPagtoService.adicionar(formaPagamento);
			
		} catch (Exception e) {
			throw new RuntimeException("Erro para atualizar a formaPagamento " + formaPagamento.getDescricao() + e);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(formaPagamento);
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamento> salvar(@PathVariable Long formaPagamentoId, @RequestBody FormaPagamento formaPagamento){
		FormaPagamento formaPagamentoAtualizado = cadastroFormaPagtoService.salvar(formaPagamentoId, formaPagamento);	
			
		return ResponseEntity.status(HttpStatus.OK).body(formaPagamentoAtualizado);
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamento> remover(@PathVariable Long formaPagamentoId) {
		try {
			cadastroFormaPagtoService.excluir(formaPagamentoId);
			return ResponseEntity.noContent().build();
			
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
