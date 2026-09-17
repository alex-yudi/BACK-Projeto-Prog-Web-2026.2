package br.edu.ufersa.pw.bairro.usuario;

import br.edu.ufersa.pw.bairro.usuario.dto.UsuarioResponse;

public interface UsuarioApi {

    UsuarioResponse buscarPorId(Long id);
}