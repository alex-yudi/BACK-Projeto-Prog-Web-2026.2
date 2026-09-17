package br.edu.ufersa.pw.bairro.api.feature.auth.dto;

public record TokenResponse(String token) {

    public TokenResponse {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("O token é obrigatório!");
        }
    }
}
