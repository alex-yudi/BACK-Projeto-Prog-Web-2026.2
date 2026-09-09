package br.edu.ufersa.pw.bairro.api.dtos.auth;

public record TokenResponse(String token) {

    public TokenResponse {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("O token é obrigatório!");
        }
    }
}
