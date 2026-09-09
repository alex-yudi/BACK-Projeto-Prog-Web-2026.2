package br.edu.ufersa.pw.bairro.api.dtos;

// Nunca inclui a senha - é o que substitui devolver a entidade Usuario direto.
public record UsuarioResponse(Long id, String nome, String email, String cep, String numero) {

    public UsuarioResponse {
        if (id == null) {
            throw new IllegalArgumentException("O id é obrigatório!");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório!");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O email é obrigatório!");
        }
        if (cep == null || cep.isBlank()) {
            throw new IllegalArgumentException("O CEP é obrigatório!");
        }
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("O número é obrigatório!");
        }
    }
}
