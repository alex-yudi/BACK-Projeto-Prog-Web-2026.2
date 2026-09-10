package br.edu.ufersa.pw.bairro.api.dtos.oferta;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OfertaUpdate(
        String nome,
        String descricao,
        BigDecimal preco,
        LocalDate validade) {

    public OfertaUpdate {
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