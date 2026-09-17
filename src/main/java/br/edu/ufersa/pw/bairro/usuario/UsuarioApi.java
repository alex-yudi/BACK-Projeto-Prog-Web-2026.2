package br.edu.ufersa.pw.bairro.api.feature.usuario;

import br.edu.ufersa.pw.bairro.api.feature.usuario.dto.UsuarioResponse;

public interface UsuarioApi {

    UsuarioResponse buscarPorId(Long id);
}