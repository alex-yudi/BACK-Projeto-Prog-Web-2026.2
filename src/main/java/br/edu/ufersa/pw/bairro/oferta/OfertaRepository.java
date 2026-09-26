package br.edu.ufersa.pw.bairro.oferta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {

    // GET /api/v1/negocios/{negocioId}/ofertas
    List<Oferta> findByNegocioId(Long negocioId);

    // GET/PUT/DELETE /negocios/{negocioId}/ofertas/{ofertaId} - garante que a oferta
    // realmente pertence ao negocio do path, e nao so que o id existe em algum lugar.
    Optional<Oferta> findByIdAndNegocioId(Long id, Long negocioId);
}

