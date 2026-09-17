package br.edu.ufersa.pw.bairro.api.feature.usuario;

import br.edu.ufersa.pw.bairro.api.feature.usuario.dto.UsuarioResponse;
import org.springframework.stereotype.Service;

// Classe package-private. Implementa o contrato público para outros módulos.
@Service
class UsuarioApiImpl implements UsuarioApi {

    private final UsuarioRepository repository;

    UsuarioApiImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = repository.findById(id).orElseThrow();
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCep(),
                usuario.getNumero()
        );
    }
}