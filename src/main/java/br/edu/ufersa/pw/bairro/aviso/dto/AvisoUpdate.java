package br.edu.ufersa.pw.bairro.aviso.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// PUT exige todos os campos deste recurso. So o autor original (ou um ADMIN) pode chamar esse endpoint
// (a checagem de quem e o autor acontece no controller/service, nao aqui).
public record AvisoUpdate(
        @NotNull(message = "A categoria é obrigatória!")
        CategoriaAviso categoria,

        @NotBlank(message = "O texto é obrigatório!")
        @Size(max = 500, message = "O texto deve ter no máximo 500 caracteres.")
        String texto
) {
}
