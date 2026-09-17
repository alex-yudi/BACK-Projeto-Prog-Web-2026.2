package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.negocio.dto.NegocioResponse;

public interface NegocioApi {

    NegocioResponse buscarPorId(Long id);
}