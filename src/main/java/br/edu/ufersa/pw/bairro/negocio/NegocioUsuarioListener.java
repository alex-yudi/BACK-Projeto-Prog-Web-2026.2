package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.usuario.UsuarioExcluidoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// Roda na transacao de quem publicou o evento: se falhar, a exclusao do usuario e desfeita junto.
@Component
class NegocioUsuarioListener {

    private final NegocioRepository repository;

    NegocioUsuarioListener(NegocioRepository repository) {
        this.repository = repository;
    }

    // Os negocios do usuario excluido ficam sem dono e podem ser reivindicados de novo.
    @EventListener
    void aoExcluirUsuario(UsuarioExcluidoEvent evento) {
        repository.removerDono(evento.usuarioId());
    }
}
