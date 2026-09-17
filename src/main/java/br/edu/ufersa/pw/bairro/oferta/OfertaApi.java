package br.edu.ufersa.pw.bairro.oferta;

import br.edu.ufersa.pw.bairro.oferta.dto.OfertaResponse;

import java.util.List;

public interface OfertaApi {

    List<OfertaResponse> listarPorNegocio(Long negocioId);
}