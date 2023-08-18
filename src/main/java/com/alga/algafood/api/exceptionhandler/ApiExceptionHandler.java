package com.alga.algafood.api.exceptionhandler;

import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.alga.algafood.domain.exception.EntidadeEmUsoException;
import com.alga.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.alga.algafood.domain.exception.MediaTypeNaoEncontradoException;
import com.alga.algafood.domain.exception.NegocioException;
import com.alga.algafood.domain.exception.PropriedadeIgnoradaException;
import com.alga.algafood.domain.exception.PropriedadeNaoReconhecidaException;
import com.alga.algafood.domain.model.enun.ProblemType;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaugth(Exception ex, WebRequest request){
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		String detail = "Ocorreu um erro interno inesperado no sistema. Tente novamente ou entre em contato com o Admistrador em caso de persistir.";
		
		ex.printStackTrace();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da reuqisicao está inválido. Verifique o erro da sintaxe";

		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format(
				"A propriedade '%s' recebeu o valor '%s', que é de um tipo invalido. Corrija e "
						+ "informe um valor compatícel com o tipo %s. ",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = e.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e,
			WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
		String detail = e.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = e.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(MediaTypeNaoEncontradoException.class)
	public ResponseEntity<?> handleHttpMediaTypeNotSupportedException(MediaTypeNaoEncontradoException e,
			WebRequest request) {
		HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
		ProblemType problemType = ProblemType.REQUISICAO_NAO_SUPORTADA;
		String detail = e.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(PropriedadeIgnoradaException.class)
	public ResponseEntity<?> handlePropriedadeIgnoradaException(PropriedadeIgnoradaException ex,
			WebRequest request) {
		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		ProblemType problemType = ProblemType.ERRO_PROPRIEDADE_IGNORADA;
		String detail = String.format("A propriedade '%s' nao foi conteúdo da requisição (body) ", path, ex.getTargetType().getSimpleName());
		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(PropriedadeNaoReconhecidaException.class)
	public ResponseEntity<?> handlePropriedadeNaoReconhecidaException(PropriedadeNaoReconhecidaException ex,
			WebRequest request) {
		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = Problem.builder().title(status.getReasonPhrase()).status(status.value()).build();

		} else if (body instanceof String) {
			body = Problem.builder().title((String) body).status(status.value()).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder().status(status.value()).type(problemType.getUri()).title(problemType.getTitle())
				.detail(detail);
	}
	
}
