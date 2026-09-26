package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.negocio.dto.NegocioResponse;

// Contrato do modulo negocio para os demais (aviso, oferta). Nao expoe entidade nem repository.
public interface NegocioApi {

    // Negocio ativo pelo id; lanca EntidadeNaoEncontradaException (404) se nao existe ou foi excluido.
    // A resposta traz o donoId (nulo = sem dono), suficiente para Usuario.exigirDonoOuAdmin(...).
    NegocioResponse buscarPorId(Long id);
}
