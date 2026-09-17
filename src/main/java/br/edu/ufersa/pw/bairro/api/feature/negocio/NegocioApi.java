package br.edu.ufersa.pw.bairro.api.feature.negocio;

import br.edu.ufersa.pw.bairro.api.feature.negocio.dto.NegocioResponse;

public interface NegocioApi {

    NegocioResponse buscarPorId(Long id);
}