package br.edu.ufersa.pw.bairro.api.dtos.negocio;

import java.time.LocalDateTime;

public record ReivindicacaoResponse(Long id, Long negocioId, Long usuarioId, String justificativa, LocalDateTime criadoEm) {

    public ReivindicacaoResponse {
        if (id == null) {
            throw new IllegalArgumentException("O id é obrigatório!");
        }
        if (negocioId == null) {
            throw new IllegalArgumentException("O negocioId é obrigatório!");
        }
        if (usuarioId == null) {
            throw new IllegalArgumentException("O usuarioId é obrigatório!");
        }
        if (criadoEm == null) {
            throw new IllegalArgumentException("A data de criação é obrigatória!");
        }
    }
}
