package br.edu.ufersa.pw.bairro.api.dtos.negocio;

// donoId nulo = perfil ainda nao reivindicado (negocio criado a partir de avisos da
// comunidade, sem dono vinculado)
public record NegocioResponse(
        Long id,
        String nome,
        CategoriaNegocio categoria,
        String cep,
        String numero,
        String descricao,
        Long donoId
) {

    public NegocioResponse {
        if (id == null) {
            throw new IllegalArgumentException("O id é obrigatório!");
        }
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
