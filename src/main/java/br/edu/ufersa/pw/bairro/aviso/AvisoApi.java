package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.aviso.dto.AvisoResponse;

import java.util.List;

public interface AvisoApi {

    List<AvisoResponse> listarPorUsuario(Long usuarioId);
}