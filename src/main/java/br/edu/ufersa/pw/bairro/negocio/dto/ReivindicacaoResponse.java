package br.edu.ufersa.pw.bairro.negocio.dto;

import java.time.LocalDateTime;

// decididaEm e decididaPorId nulos enquanto PENDENTE.
public record ReivindicacaoResponse(
        Long id,
        Long negocioId,
        Long usuarioId,
        String justificativa,
        StatusReivindicacao status,
        LocalDateTime criadoEm,
        LocalDateTime decididaEm,
        Long decididaPorId
) { }
