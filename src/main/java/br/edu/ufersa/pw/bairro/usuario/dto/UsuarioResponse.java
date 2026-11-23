package br.edu.ufersa.pw.bairro.usuario.dto;

// Nunca inclui a senha - é o que substitui devolver a entidade Usuario direto.
public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String cep,
        String numero
) { }