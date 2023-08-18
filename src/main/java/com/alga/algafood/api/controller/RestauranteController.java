package com.alga.algafood.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils.FieldFilter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alga.algafood.domain.exception.EntidadeEmUsoException;
import com.alga.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.alga.algafood.domain.model.Restaurante;
import com.alga.algafood.domain.repository.RestauranteRepository;
import com.alga.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	CadastroRestauranteService cadastroRestauranteService;

	@Autowired
	RestauranteRepository restauranteRepository;

	@GetMapping(produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public List<Restaurante> listar() {
		return cadastroRestauranteService.listar();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscar(restauranteId);

		return ResponseEntity.status(HttpStatus.OK).body(restaurante);
	}

	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxaFrete(String nome, BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal) {
		return restauranteRepository.consultar(nome, taxaFreteInicial, taxaFreteFinal);
	}

	@GetMapping("/restaurantes/porNomeEid")
	public List<Restaurante> restaurantesPorNomeEid(String nome, Long cozinhaId) {
		return restauranteRepository.consultarPorNome(nome, cozinhaId);
	}

	@GetMapping("/restaurantes/primeiroPorNome")
	public Optional<Restaurante> restaurantePrimeiroPorNome(String nome) {
		return restauranteRepository.findFirstByNomeContaining(nome);
	}

	@GetMapping("/restaurantes/comFreteGratis")
	public List<Restaurante> restaurantescomFreteGratis(String nome) {

		return restauranteRepository.consultarComFreteGratis(nome);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> salvar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		try {
			Restaurante restauranteAtualizado = cadastroRestauranteService.salvar(restauranteId, restaurante);

			return ResponseEntity.status(HttpStatus.OK).body(restauranteAtualizado);

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Restaurante> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestauranteService.adicionar(restaurante);

		} catch (Exception e) {
			throw new RuntimeException("Erro para atualizar o restaurante " + e);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);

	}

	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId) {
		try {
			cadastroRestauranteService.excluir(restauranteId);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
			@RequestBody Map<String, Object> campos, HttpServletRequest request) {

		Restaurante restauranteAtual = cadastroRestauranteService.buscar(restauranteId);

		merge(campos, restauranteAtual, request);

		return salvar(restauranteId, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {

		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
	try {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(objectMapper, Restaurante.class);
		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findRequiredField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);

			Object novoValor = ReflectionUtils.findField(getClass(), (FieldFilter) restauranteOrigem);

			System.out.println(nomePropriedade + " = " + valorPropriedade);

			ReflectionUtils.setField(field, restauranteDestino, novoValor);

			});
		
		}catch(IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
			 
		}
	}
}
