package com.alga.algafood.domain.exception;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

public class PropriedadeNaoReconhecidaException extends UnrecognizedPropertyException{

	private static final long serialVersionUID = 1L;
	
	public PropriedadeNaoReconhecidaException(JsonParser p, String msg, JsonLocation loc, Class<?> referringClass,
			String propName, Collection<Object> propertyIds) {
		super(p, msg, loc, referringClass, propName, propertyIds);
	}

}
