package br.edu.ufersa.pw.bairro.negocio.dto;

import jakarta.validation.constraints.NotNull;

// PATCH /api/v1/reivindicacoes/{id} - usado pelo ADMIN para aprovar ou rejeitar
public record ReivindicacaoDecisao(
                @NotNull(message = "A decisão é obrigatória!") DecisaoReivindicacao status) {
}
