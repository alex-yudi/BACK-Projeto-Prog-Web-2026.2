package br.edu.ufersa.pw.bairro.negocio.dto;

import jakarta.validation.constraints.Size;

// justificativa é opcional, mas se vier, não pode ser vazia
// Os ids do negócio e do usuário são obtidos pelo próprio contexto da requisição
public record ReivindicacaoRequest(
        // @Size permite nulo, mas barra string vazia (tamanho menor que 1)
        @Size(min = 1, message = "A justificativa não pode ser vazia!")
        String justificativa
) { }