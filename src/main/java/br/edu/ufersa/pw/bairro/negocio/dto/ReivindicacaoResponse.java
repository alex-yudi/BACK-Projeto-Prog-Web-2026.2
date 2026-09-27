package br.edu.ufersa.pw.bairro.negocio.dto;

import java.time.LocalDateTime;

public record ReivindicacaoResponse(
        Long id,
        Long negocioId,
        Long usuarioId,
        String justificativa,
        LocalDateTime criadoEm
) { }