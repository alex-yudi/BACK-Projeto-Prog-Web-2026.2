package br.edu.ufersa.pw.bairro.api.dtos.negocio;

// justificativa é opcional, mas se vier, não pode ser vazia
// Os ids do negócio e do usuário são obtidos pelo próprio contexto da requisição
public record ReivindicacaoRequest(String justificativa) {
    public ReivindicacaoRequest {
        if (justificativa != null && justificativa.isBlank()) {
            throw new IllegalArgumentException("A justificativa não pode ser vazia!");
        }
    }
}
