package br.edu.ufersa.pw.bairro.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;

// Trata so as excecoes de negocio. AuthenticationException e AccessDeniedException nao passam
// por aqui de proposito: quem responde por elas e o Spring Security.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Violacao de regra de negocio/duplicidade (HTTP 422 Unprocessable Entity)
    @ExceptionHandler(OperacaoInvalidaException.class)
    public ProblemDetail tratarOperacaoInvalida(OperacaoInvalidaException ex) {
        return problema(HttpStatus.UNPROCESSABLE_ENTITY, "Regra de negócio violada", ex.getMessage());
    }

    // Coringa para as demais excecoes derivadas de NegocioException
    @ExceptionHandler(NegocioException.class)
    public ProblemDetail tratarNegocioGenerico(NegocioException ex) {
        return problema(HttpStatus.BAD_REQUEST, "Violação de regra de negócio", ex.getMessage());
    }

    private ProblemDetail problema(HttpStatus status, String titulo, String detalhe) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detalhe);
        problem.setType(URI.create("about:blank"));
        problem.setTitle(titulo);
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }
}
