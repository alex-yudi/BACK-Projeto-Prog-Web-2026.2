package br.edu.ufersa.pw.bairro.aviso.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// negocioId vem do path (/negocios/{negocioId}/avisos), autor vem do usuario autenticado -
// nenhum dos dois é campo deste DTO.
public record AvisoCreate(
        @NotNull(message = "A categoria é obrigatória!")
        CategoriaAviso categoria,

        @NotBlank(message = "O texto é obrigatório!")
        @Size(max = 500, message = "O texto deve ter no máximo 500 caracteres.")
        String texto
) {
}
