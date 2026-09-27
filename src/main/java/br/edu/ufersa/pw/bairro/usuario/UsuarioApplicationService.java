package br.edu.ufersa.pw.bairro.usuario;

import br.edu.ufersa.pw.bairro.aviso.AvisoApi;
import br.edu.ufersa.pw.bairro.aviso.dto.AvisoResponse;
import br.edu.ufersa.pw.bairro.usuario.dto.UsuarioAtualizacaoRequest;
import br.edu.ufersa.pw.bairro.usuario.dto.UsuarioRegistroRequest;
import br.edu.ufersa.pw.bairro.usuario.dto.UsuarioResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class UsuarioApplicationService {

    private final UsuarioDomainService domainService;
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AvisoApi avisoApi; // Injeção da interface do outro módulo

    UsuarioApplicationService(UsuarioDomainService domainService,
                              UsuarioRepository repository,
                              PasswordEncoder passwordEncoder,
                              AvisoApi avisoApi) {
        this.domainService = domainService;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.avisoApi = avisoApi;
    }

    @Transactional
    public UsuarioResponse cadastrar(UsuarioRegistroRequest request) {
        domainService.validarEmailDisponivel(request.email());

        Usuario usuario = new Usuario(
                request.nome(),
                request.email(),
                passwordEncoder.encode(request.senha()),
                request.cep(),
                request.numero()
        );

        Usuario salvo = repository.save(usuario);

        return mapearParaResponse(salvo);
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPerfil(Usuario usuarioAutenticado) {
        return mapearParaResponse(usuarioAutenticado);
    }

    @Transactional
    public UsuarioResponse atualizarPerfil(Usuario usuarioAutenticado, UsuarioAtualizacaoRequest request) {
        if (!usuarioAutenticado.getEmail().equals(request.email())) {
            domainService.validarEmailDisponivel(request.email());
        }

        usuarioAutenticado.atualizarPerfil(
                request.nome(),
                request.email(),
                request.cep(),
                request.numero()
        );

        Usuario salvo = repository.save(usuarioAutenticado);

        return mapearParaResponse(salvo);
    }

    @Transactional
    public void excluirPerfil(Usuario usuarioAutenticado) {
        repository.delete(usuarioAutenticado);
    }

    @Transactional(readOnly = true)
    public List<AvisoResponse> listarAvisosDoUsuario(Usuario usuarioAutenticado, Integer page, Integer size) {
        // Busca real através do contrato entre módulos
        return avisoApi.buscarPorAutorId(usuarioAutenticado.getId(), page, size);
    }

    private UsuarioResponse mapearParaResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCep(),
                usuario.getNumero()
        );
    }
}