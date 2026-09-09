package br.edu.ufersa.pw.bairro.api.dtos;

// PUT exige todos os dados (padrão da disciplina) - inclusive senha, diferente do
// fluxo real de "deixe em branco para manter a atual". PATCH seria o lugar certo
// para atualização parcial, se/quando for adicionado.
public record UsuarioAtualizacaoRequest(String nome, String email, String senha, String cep, String numero) {

    public UsuarioAtualizacaoRequest {
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
