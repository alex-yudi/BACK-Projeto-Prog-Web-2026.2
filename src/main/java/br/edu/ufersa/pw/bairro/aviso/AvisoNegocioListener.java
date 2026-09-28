package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.negocio.NegocioExcluidoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// Roda na transacao de quem publicou o evento: se falhar, a exclusao do negocio e desfeita junto.
@Component
class AvisoNegocioListener {

    private final AvisoRepository repository;

    AvisoNegocioListener(AvisoRepository repository) {
        this.repository = repository;
    }

    // Os avisos do negocio excluido saem de toda consulta (soft delete).
    @EventListener
    void aoExcluirNegocio(NegocioExcluidoEvent evento) {
        repository.excluirPorNegocio(evento.negocioId(), LocalDateTime.now());
    }
}
