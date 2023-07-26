package com.alga.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.alga.algafood.domain.exception.EntidadeEmUsoException;
import com.alga.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.alga.algafood.domain.exception.NegocioException;
import com.alga.algafood.domain.model.Restaurante;
import com.alga.algafood.domain.repository.CozinhaRepository;
import com.alga.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String RESTAURANTE_EM_CONFLITO = "Restaurante de código %d não pode ser removida, está em uso";
	private static final String RESTAURANTE_NAO_ENCONTRADO = "Nao existe um cadastro de Restaurante com o código %d";

	@Autowired
	RestauranteRepository restauranteRepository;

	@Autowired
	CozinhaRepository cozinhaRepository;

	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	public Restaurante buscar(Long retauranteId) {
		return buscarOuFalhar(retauranteId);
	}

	public Restaurante salvar(Long restauranteId, Restaurante restaurante) {
		Restaurante restauranteAtual = buscar(restauranteId);

		// para assumir todas as propriedades
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro");
		try {
			return restauranteRepository.save(restauranteAtual);

		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	public Restaurante adicionar(Restaurante restaurante) {
		return restauranteRepository.save(restaurante);
	}

	public void excluir(Long restauranteId) {
		Restaurante restauranteAtual = buscar(restauranteId);

		try {
			restauranteRepository.deleteById(restauranteAtual.getId());

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(RESTAURANTE_NAO_ENCONTRADO, restauranteId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(RESTAURANTE_EM_CONFLITO, restauranteId));
		}
	}

	public Restaurante buscarOuFalhar(Long retauranteId) {
		return restauranteRepository.findById(retauranteId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(RESTAURANTE_NAO_ENCONTRADO, retauranteId)));
	}
}
