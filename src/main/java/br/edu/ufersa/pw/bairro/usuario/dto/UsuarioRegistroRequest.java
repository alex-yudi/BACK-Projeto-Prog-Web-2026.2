package br.edu.ufersa.pw.bairro.usuario.dto;

public record UsuarioRegistroRequest(String nome, String email, String senha, String cep, String numero) {

    public UsuarioRegistroRequest {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório!");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O email é obrigatório!");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("O email informado não é válido!");
        }
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória!");
        }
        if (senha.length() < 8 || senha.length() > 32) {
            throw new IllegalArgumentException("A senha deve ter entre 8 e 32 caracteres!");
        }
        if (cep == null || cep.isBlank()) {
            throw new IllegalArgumentException("O CEP é obrigatório!");
        }
        if (!cep.matches("\\d{8}")) {
            throw new IllegalArgumentException("O CEP deve conter exatamente 8 dígitos!");
        }
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("O número é obrigatório!");
        }
    }
}
