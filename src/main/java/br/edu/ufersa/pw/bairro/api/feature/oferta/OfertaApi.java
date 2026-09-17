package br.edu.ufersa.pw.bairro.api.feature.oferta;

import br.edu.ufersa.pw.bairro.api.feature.oferta.dto.OfertaResponse;

import java.util.List;

public interface OfertaApi {

    List<OfertaResponse> listarPorNegocio(Long negocioId);
}