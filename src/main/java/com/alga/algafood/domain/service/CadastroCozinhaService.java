package com.alga.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.alga.algafood.domain.exception.EntidadeEmUsoException;
import com.alga.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.alga.algafood.domain.model.Cozinha;
import com.alga.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, está em uso";
	private static final String COZINHA_NAO_ENCONTRADA = "Nao Existe um cadastro de cozinha com codigo %d";

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	public void excluir(Long cozinhaId) {

		Cozinha cozinhaAtual = buscarOuFalhar(cozinhaId);

		try {
			cozinhaRepository.deleteById(cozinhaAtual.getId());

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(COZINHA_EM_USO, cozinhaId));
		}

	}

	public Cozinha buscarOuFalhar(@PathVariable Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(COZINHA_NAO_ENCONTRADA, cozinhaId)));

	}

}
