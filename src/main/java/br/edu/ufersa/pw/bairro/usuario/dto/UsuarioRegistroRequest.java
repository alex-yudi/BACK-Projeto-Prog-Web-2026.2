package br.edu.ufersa.pw.bairro.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioRegistroRequest(
        @NotBlank(message = "O nome é obrigatório!")
        String nome,

        @NotBlank(message = "O email é obrigatório!")
        @Email(message = "O email informado não é válido!")
        String email,

        @NotBlank(message = "A senha é obrigatória!")
        @Size(min = 8, max = 32, message = "A senha deve ter entre 8 e 32 caracteres!")
        String senha,

        @NotBlank(message = "O CEP é obrigatório!")
        @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos!")
        String cep,

        @NotBlank(message = "O número é obrigatório!")
        String numero
) {
}