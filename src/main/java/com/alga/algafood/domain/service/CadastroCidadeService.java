package com.alga.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.alga.algafood.domain.exception.EntidadeEmUsoException;
import com.alga.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.alga.algafood.domain.exception.EstadoNaoEncontradoException;
import com.alga.algafood.domain.exception.NegocioException;
import com.alga.algafood.domain.model.Cidade;
import com.alga.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	private static final String CIDADE_EM_CONFLITO = "Restaurante de código %d não pode ser removida, está em uso";
	private static final String CIDADE_NAO_ENCONTRADA = "Nao existe um cadastro de restaurante com o código %d";

	@Autowired
	CidadeRepository cidadeRepository;

	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}

	public Cidade buscar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(CIDADE_NAO_ENCONTRADA, cidadeId)));
	}

	public Cidade salvar(Long cidadeId, Cidade cidade) {
		Cidade cidadeColeta = buscar(cidadeId);
		Cidade cidadeAtual = new Cidade();

		// para assumir todas as propriedades
		BeanUtils.copyProperties(cidade, cidadeColeta, "id");

		try {
			cidadeAtual = cidadeRepository.save(cidadeColeta);

			return cidadeAtual;

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}

	}

	public Cidade adicionar(Cidade cidade) {
		return cidadeRepository.save(cidade);
	}

	public void excluir(Long cidadeId) {
		Cidade cidadeColeta = buscar(cidadeId);

		try {
			cidadeRepository.deleteById(cidadeColeta.getId());

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(CIDADE_NAO_ENCONTRADA, cidadeId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(CIDADE_EM_CONFLITO, cidadeId));
		
		}catch(RuntimeException e) {
			throw new NegocioException(String.format("Erro não mapeado para a execução", cidadeId));
		}

	}
}
