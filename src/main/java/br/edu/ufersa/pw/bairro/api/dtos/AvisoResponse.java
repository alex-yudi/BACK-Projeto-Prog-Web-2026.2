package br.edu.ufersa.pw.bairro.api.dtos;

public record AvisoResponse(Long id, Long negocioId, String categoria, String texto) {

    public AvisoResponse {
        if (id == null) {
            throw new IllegalArgumentException("O id é obrigatório!");
        }
        if (negocioId == null) {
            throw new IllegalArgumentException("O negocioId é obrigatório!");
        }
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("A categoria é obrigatória!");
        }
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("O texto é obrigatório!");
        }
    }
}
