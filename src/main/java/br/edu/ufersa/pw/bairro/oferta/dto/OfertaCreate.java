package br.edu.ufersa.pw.bairro.oferta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OfertaCreate(
        @NotBlank(message = "O nome é obrigatório!")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
        String nome,

        @NotBlank(message = "A descrição é obrigatória!")
        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres.")
        String descricao,

        @NotNull(message = "O preço é obrigatório!")
        @PositiveOrZero(message = "O preço não pode ser negativo!")
        BigDecimal preco,

        LocalDate validade) {
}
