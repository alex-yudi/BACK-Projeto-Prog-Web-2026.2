package br.edu.ufersa.pw.bairro.oferta;

import br.edu.ufersa.pw.bairro.negocio.NegocioExcluidoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// Roda na transacao de quem publicou o evento: se falhar, a exclusao do negocio e desfeita junto.
@Component
class OfertaNegocioListener {

    private final OfertaRepository repository;

    OfertaNegocioListener(OfertaRepository repository) {
        this.repository = repository;
    }

    // As ofertas do negocio excluido saem de toda consulta (soft delete).
    @EventListener
    void aoExcluirNegocio(NegocioExcluidoEvent evento) {
        repository.excluirPorNegocio(evento.negocioId(), LocalDateTime.now());
    }
}
