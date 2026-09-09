package br.edu.ufersa.pw.bairro.api.dtos.aviso;

import java.time.LocalDateTime;

// votosUtil/votosNaoUtil sao a contagem agregada das Avaliacoes desse aviso - nao existe
// coluna propria pra isso, e o Response que resume o que o front precisa exibir.
public record AvisoResponse(
        Long id,
        Long negocioId,
        Long autorId,
        CategoriaAviso categoria,
        String texto,
        LocalDateTime criadoEm,
        long votosUtil,
        long votosNaoUtil
) {

    public AvisoResponse {
        if (id == null) {
            throw new IllegalArgumentException("O id é obrigatório!");
        }
        if (negocioId == null) {
            throw new IllegalArgumentException("O negocioId é obrigatório!");
        }
        if (autorId == null) {
            throw new IllegalArgumentException("O autorId é obrigatório!");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria é obrigatória!");
        }
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("O texto é obrigatório!");
        }
        if (criadoEm == null) {
            throw new IllegalArgumentException("A data de criação é obrigatória!");
        }
    }
}
