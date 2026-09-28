package br.edu.ufersa.pw.bairro.negocio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// PUT exige todos os campos deste recurso. So o dono do negocio (ou um ADMIN) pode chamar esse
// endpoint (checagem acontece no controller/service, nao aqui).
public record NegocioUpdate(
        @NotBlank(message = "O nome é obrigatório!")
        String nome,

        @NotNull(message = "A categoria é obrigatória!")
        CategoriaNegocio categoria,

        @NotBlank(message = "O CEP é obrigatório!")
        @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos!")
        String cep,

        @NotBlank(message = "O número é obrigatório!")
        String numero,

        @NotBlank(message = "O bairro é obrigatório!")
        String bairro,

        @NotBlank(message = "A descrição é obrigatória!")
        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres.")
        String descricao
) {
}
