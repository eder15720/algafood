package com.alga.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alga.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.alga.algafood.domain.model.Cozinha;
import com.alga.algafood.domain.repository.CozinhaRepository;
import com.alga.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@GetMapping(produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{cozinhaId}")
	public Cozinha buscar(@PathVariable Long cozinhaId) {
		return cadastroCozinha.buscarOuFalhar(cozinhaId);
	}

	@GetMapping("/porNome")
	public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome) {
		return cozinhaRepository.findByNomeContaining(nome);
	}

	@GetMapping("/UnicaPorNome")
	public Optional<Cozinha> cozinhaUnicaPorNome(@RequestParam("nome") String nome) {
		return cozinhaRepository.findUnicaByNome(nome);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha) {
		Cozinha cozinhaCriada = cadastroCozinha.salvar(cozinha);

		return ResponseEntity.ok(cozinhaCriada);
	}

	@PutMapping("/{cozinhaId}")
	public Cozinha atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);

			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

			return cadastroCozinha.salvar(cozinhaAtual);
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Cozinha> removerExemplos(@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
		return ResponseEntity.noContent().build();
	}

}
