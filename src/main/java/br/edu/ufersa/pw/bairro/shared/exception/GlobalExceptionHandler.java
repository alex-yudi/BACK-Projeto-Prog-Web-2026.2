package br.edu.ufersa.pw.bairro.shared.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.sql.SQLException;
import java.time.Instant;

// AuthenticationException e AccessDeniedException do Spring Security nao passam por aqui de proposito:
// quem responde por elas e o Spring Security.
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String SQLSTATE_UNICIDADE = "23505";
    private static final String SQLSTATE_TAMANHO = "22001";

    // Violacao de regra de negocio/duplicidade (HTTP 422 Unprocessable Entity)
    @ExceptionHandler(OperacaoInvalidaException.class)
    public ProblemDetail tratarOperacaoInvalida(OperacaoInvalidaException ex) {
        return problema(HttpStatus.UNPROCESSABLE_ENTITY, "Regra de negócio violada", ex.getMessage());
    }

    // Recurso inexistente ou ja excluido (HTTP 404)
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ProblemDetail tratarNaoEncontrada(EntidadeNaoEncontradaException ex) {
        return problema(HttpStatus.NOT_FOUND, "Recurso não encontrado", ex.getMessage());
    }

    // Usuario autenticado, mas sem permissao sobre o recurso (HTTP 403)
    @ExceptionHandler(AcessoNegadoException.class)
    public ProblemDetail tratarAcessoNegado(AcessoNegadoException ex) {
        return problema(HttpStatus.FORBIDDEN, "Acesso negado", ex.getMessage());
    }

    // Coringa para as demais excecoes derivadas de NegocioException
    @ExceptionHandler(NegocioException.class)
    public ProblemDetail tratarNegocioGenerico(NegocioException ex) {
        return problema(HttpStatus.BAD_REQUEST, "Violação de regra de negócio", ex.getMessage());
    }

    // JSON malformado ou reprovado pelo construtor do DTO (HTTP 400). Quando a validacao do DTO e a
    // causa, o Jackson embrulha a IllegalArgumentException; devolvemos a mensagem dela.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail tratarCorpoIlegivel(HttpMessageNotReadableException ex) {
        return problema(HttpStatus.BAD_REQUEST, "Corpo da requisição inválido", mensagemDaValidacao(ex));
    }

    // Duas requisicoes alteraram o mesmo registro ao mesmo tempo (HTTP 409)
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ProblemDetail tratarConcorrencia(OptimisticLockingFailureException ex) {
        return problema(HttpStatus.CONFLICT, "Conflito", "O registro foi alterado por outra requisição. Tente novamente.");
    }

    // Restricao do banco. Violacao de unicidade (corrida entre duas requisicoes) e conflito (409);
    // qualquer outra e erro nosso: fica no log e o cliente recebe 500 sem detalhes internos.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail tratarIntegridade(DataIntegrityViolationException ex) {
        if (ex.getMostSpecificCause() instanceof SQLException sql) {
            if (SQLSTATE_UNICIDADE.equals(sql.getSQLState())) {
                return problema(HttpStatus.CONFLICT, "Conflito", "Já existe um registro com esses dados.");
            }
            if (SQLSTATE_TAMANHO.equals(sql.getSQLState())) {
                return problema(HttpStatus.BAD_REQUEST, "Corpo da requisição inválido", "Um dos campos excede o tamanho permitido.");
            }
        }
        log.error("Violacao de integridade nao esperada", ex);
        return problema(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno", "Não foi possível concluir a operação.");
    }

    private String mensagemDaValidacao(Throwable ex) {
        for (Throwable t = ex; t != null; t = t.getCause()) {
            if (t instanceof IllegalArgumentException) {
                return t.getMessage();
            }
        }
        return "O corpo da requisição está malformado ou contém um valor inválido.";
    }

    private ProblemDetail problema(HttpStatus status, String titulo, String detalhe) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detalhe);
        problem.setType(URI.create("about:blank"));
        problem.setTitle(titulo);
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }
}
