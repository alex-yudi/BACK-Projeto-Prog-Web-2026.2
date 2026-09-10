package br.edu.ufersa.pw.bairro.api.dtos.oferta;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OfertaResponse(
        Long id,
        Long negocioId,
        String nome,
        String descricao,
        BigDecimal preco,
        LocalDate validade
) {

    public OfertaResponse {
        if (id == null) {
            throw new IllegalArgumentException("O id é obrigatório!");
        }

        if (negocioId == null) {
            throw new IllegalArgumentException("O negocioId é obrigatório!");
        }

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório!");
        }

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição é obrigatória!");
        }

        if (preco == null) {
            throw new IllegalArgumentException("O preço é obrigatório!");
        }

        if (preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo!");
        }
    }
}