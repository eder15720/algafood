package com.alga.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RequisicaoInvalidaException extends ResponseStatusException{

	private static final long serialVersionUID = 1L;
	
	public RequisicaoInvalidaException(HttpStatus status, String mensagem) {
		super(status, mensagem);
	}
	public RequisicaoInvalidaException(String mensagem) {
		this(HttpStatus.INTERNAL_SERVER_ERROR, mensagem);
	}

}
