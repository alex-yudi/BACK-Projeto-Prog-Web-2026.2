package br.edu.ufersa.pw.bairro.api.dtos;

public record UsuarioRegistroRequest(String nome, String email, String senha, String cep, String numero) {

    public UsuarioRegistroRequest {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório!");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O email é obrigatório!");
        }
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória!");
        }
        if (cep == null || cep.isBlank()) {
            throw new IllegalArgumentException("O CEP é obrigatório!");
        }
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("O número é obrigatório!");
        }
    }
}
