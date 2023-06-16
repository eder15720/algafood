package com.alga.algafood.domain.repository;

import java.util.List;

import com.alga.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {
	
	List<FormaPagamento> listar();
	FormaPagamento porId(Long id);
	FormaPagamento salvar(FormaPagamento formaPagto);
	void remover(FormaPagamento formaPagto);

}
