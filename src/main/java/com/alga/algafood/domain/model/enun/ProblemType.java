package com.alga.algafood.domain.model.enun;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade nao encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	MENSAGEM_INCOMPREENSIVEL("requisicao-incompreensivel", "requisicao com erro nao identificado"),
	REQUISICAO_NAO_SUPORTADA("/media-type-erro", "Tipo da requisicao nao suportada para envio"),
	ERRO_NEGOCIO("/requisicao-com-erro", "Erro nao previsto durante a execucao da requisicao"),
	ERRO_PROPRIEDADE_IGNORADA("requisicao-incompreensivel", "propriedade/campo de preenchimento ignorada durante o envio"),
	ERRO_DE_SISTEMA("erro-sistemico", "erro nao previsto ocorrido");
	
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title){
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}

}
