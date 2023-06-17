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
import com.alga.algafood.domain.model.Cidade;
import com.alga.algafood.domain.model.Restaurante;
import com.alga.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {
	
	@Autowired
	CadastroCidadeService cadastroCidadeService;
	
	@GetMapping(produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Cidade>> listar() {
		List<Cidade> cidades = cadastroCidadeService.listar();
		
		return ResponseEntity.status(HttpStatus.OK).body(cidades);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cidade> adicionar(@RequestBody Cidade cidade){
		try {	
			cidade = cadastroCidadeService.adicionar(cidade);
			
		} catch (Exception e) {
			throw new RuntimeException("Erro para atualizar a cidade " + cidade.getNome() + e);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{cidadeId}")
	public ResponseEntity<Cidade> salvar(@PathVariable Long cidadeId, @RequestBody Cidade cidade){
		Cidade cidadeAtualizada = cadastroCidadeService.salvar(cidadeId, cidade);	
			
		return ResponseEntity.status(HttpStatus.OK).body(cidadeAtualizada);
	}
	
	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<Cidade> remover(@PathVariable Long cidadeId) {
		try {
			cadastroCidadeService.excluir(cidadeId);
			return ResponseEntity.noContent().build();
			
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
