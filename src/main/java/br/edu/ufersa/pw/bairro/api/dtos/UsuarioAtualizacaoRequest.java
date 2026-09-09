package br.edu.ufersa.pw.bairro.api.dtos;

// PUT exige todos os campos DESTE recurso (padrão da disciplina) - mas senha não é
// um deles. Trocar senha é uma ação à parte (normalmente exige confirmar a senha
// atual), não um campo comum de perfil que se reenvia a cada edição.
public record UsuarioAtualizacaoRequest(String nome, String email, String cep, String numero) {

    public UsuarioAtualizacaoRequest {
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
