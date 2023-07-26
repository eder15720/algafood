package com.alga.algafood.domain.exception;

import org.springframework.web.HttpMediaTypeNotSupportedException;

public class MediaTypeNaoEncontradoException extends HttpMediaTypeNotSupportedException{

	private static final long serialVersionUID = 1L;
	
	public MediaTypeNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public MediaTypeNaoEncontradoException(Long estadoId) {
		this(String.format("O formato enviado não foi aceito %d", estadoId));
	}

}
