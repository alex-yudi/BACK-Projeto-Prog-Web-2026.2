package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.usuario.UsuarioExcluidoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// Roda na transacao de quem publicou o evento: se falhar, a exclusao do usuario e desfeita junto.
@Component
class AvisoUsuarioListener {

    private final AvisoRepository avisoRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    AvisoUsuarioListener(AvisoRepository avisoRepository, AvaliacaoRepository avaliacaoRepository) {
        this.avisoRepository = avisoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    // Os avisos e os votos do usuario excluido saem da listagem e da contagem (D3).
    @EventListener
    void aoExcluirUsuario(UsuarioExcluidoEvent evento) {
        LocalDateTime agora = LocalDateTime.now();
        avisoRepository.excluirPorAutor(evento.usuarioId(), agora);
        avaliacaoRepository.excluirPorUsuario(evento.usuarioId(), agora);
    }
}
