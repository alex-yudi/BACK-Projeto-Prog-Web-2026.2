package br.edu.ufersa.pw.bairro.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// PUT exige todos os campos DESTE recurso (padrão da disciplina) - mas senha não é
// um deles. Trocar senha é uma ação à parte (normalmente exige confirmar a senha
// atual), não um campo comum de perfil que se reenvia a cada edição.
public record UsuarioAtualizacaoRequest(
        @NotBlank(message = "O nome é obrigatório!")
        String nome,

        @NotBlank(message = "O email é obrigatório!")
        @Email(message = "O email informado não é válido!")
        String email,

        @NotBlank(message = "O CEP é obrigatório!")
        @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos!")
        String cep,

        @NotBlank(message = "O número é obrigatório!")
        String numero
) {
}