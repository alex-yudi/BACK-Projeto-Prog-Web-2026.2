package br.edu.ufersa.pw.bairro.api.dtos.negocio;

// dono vem do usuario autenticado - nao e campo deste DTO.
public record NegocioCreate(String nome, CategoriaNegocio categoria, String cep, String numero, String descricao) {

    public NegocioCreate {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório!");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria é obrigatória!");
        }
        if (cep == null || cep.isBlank()) {
            throw new IllegalArgumentException("O CEP é obrigatório!");
        }
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("O número é obrigatório!");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição é obrigatória!");
        }
    }
}
