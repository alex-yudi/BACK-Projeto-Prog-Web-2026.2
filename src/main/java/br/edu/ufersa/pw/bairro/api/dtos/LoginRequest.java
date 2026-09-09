package br.edu.ufersa.pw.bairro.api.dtos;

public record LoginRequest(String email, String senha) {

    public LoginRequest {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O email é obrigatório!");
        }
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória!");
        }
    }
}
