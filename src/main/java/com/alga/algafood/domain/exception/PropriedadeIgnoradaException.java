package com.alga.algafood.domain.exception;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;

public class PropriedadeIgnoradaException extends IgnoredPropertyException {

	private static final long serialVersionUID = 1L;

	public PropriedadeIgnoradaException(JsonParser p, String msg, JsonLocation loc, Class<?> referringClass,
			String propName, Collection<Object> propertyIds) {
		super(p, msg, loc, referringClass, propName, propertyIds);
	}

}
