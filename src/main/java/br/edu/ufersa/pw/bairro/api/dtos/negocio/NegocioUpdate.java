package br.edu.ufersa.pw.bairro.api.dtos.negocio;

// PUT exige todos os campos deste recurso. So o dono do negocio pode chamar esse
// endpoint (checagem acontece no controller/service, nao aqui).
public record NegocioUpdate(String nome, CategoriaNegocio categoria, String cep, String numero, String descricao) {

    public NegocioUpdate {
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
