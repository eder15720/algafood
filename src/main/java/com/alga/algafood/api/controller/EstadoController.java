package com.alga.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.alga.algafood.domain.model.Estado;
import com.alga.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	CadastroEstadoService cadastroEstadoService;

	@GetMapping(produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Estado>> listar() {
		List<Estado> estados = cadastroEstadoService.listar();

		return ResponseEntity.status(HttpStatus.OK).body(estados);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Estado> adicionar(@RequestBody @Valid Estado estado) {
		try {
			estado = cadastroEstadoService.adicionar(estado);

		} catch (Exception e) {
			throw new RuntimeException("Erro para atualizar a estado " + estado.getNome() + e);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(estado);

	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{estadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable Long estadoId, @RequestBody Estado estado) {
		Estado estadoColetado = cadastroEstadoService.buscar(estadoId);

		return ResponseEntity.status(HttpStatus.OK).body(estadoColetado);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{estadoId}")
	public ResponseEntity<Estado> salvar(@PathVariable @Valid Long estadoId, @RequestBody Estado estado) {
		Estado estadoAtualizado = cadastroEstadoService.salvar(estadoId, estado);

		return ResponseEntity.status(HttpStatus.OK).body(estadoAtualizado);
	}

	@DeleteMapping("/{estadoId}")
	public void remover(@PathVariable Long estadoId) {
		cadastroEstadoService.excluir(estadoId);
	}
}
