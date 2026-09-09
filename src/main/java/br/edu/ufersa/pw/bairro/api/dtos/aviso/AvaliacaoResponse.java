package br.edu.ufersa.pw.bairro.api.dtos.aviso;

import java.time.LocalDateTime;

public record AvaliacaoResponse(Long id, Long avisoId, Long usuarioId, boolean util, LocalDateTime criadoEm) {

    public AvaliacaoResponse {
        if (id == null) {
            throw new IllegalArgumentException("O id é obrigatório!");
        }
        if (avisoId == null) {
            throw new IllegalArgumentException("O avisoId é obrigatório!");
        }
        if (usuarioId == null) {
            throw new IllegalArgumentException("O usuarioId é obrigatório!");
        }
        if (criadoEm == null) {
            throw new IllegalArgumentException("A data de criação é obrigatória!");
        }
    }
}
