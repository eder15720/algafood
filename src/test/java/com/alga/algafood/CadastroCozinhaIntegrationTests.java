package com.alga.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alga.algafood.domain.exception.EntidadeEmUsoException;
import com.alga.algafood.domain.model.Cozinha;
import com.alga.algafood.domain.service.CadastroCozinhaService;

@RunWith(SpringRunner.class)
@SpringBootTest
class CadastroCozinhaIntegrationTests {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha; 
	
	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComSucesso() {
		// cenário
		
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		//ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		//validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	
	@Test
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
	}
	
	@Test(expected = EntidadeEmUsoException.class)
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		
		cadastroCozinha.excluir(1L);
		
	}

}
