package com.alga.algafood.domain.repository;

import java.util.List;

import com.alga.algafood.domain.model.Permissao;

public interface PermissaoRepository {
	
	List<Permissao> listar();
	Permissao porId(Long id);
	Permissao salvar(Permissao permissao);
	void remover(Permissao permissao);

}
