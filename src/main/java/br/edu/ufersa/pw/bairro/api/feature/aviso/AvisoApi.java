package br.edu.ufersa.pw.bairro.api.feature.aviso;

import br.edu.ufersa.pw.bairro.api.feature.aviso.dto.AvisoResponse;

import java.util.List;

public interface AvisoApi {

    List<AvisoResponse> listarPorUsuario(Long usuarioId);
}