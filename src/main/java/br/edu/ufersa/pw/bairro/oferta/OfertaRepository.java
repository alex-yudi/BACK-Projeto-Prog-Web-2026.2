package br.edu.ufersa.pw.bairro.oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {

    // GET /api/v1/negocios/{negocioId}/ofertas
    List<Oferta> findByNegocioId(Long negocioId);

    // GET/PUT/DELETE /negocios/{negocioId}/ofertas/{ofertaId} - garante que a oferta
    // realmente pertence ao negocio do path, e nao so que o id existe em algum lugar.
    Optional<Oferta> findByIdAndNegocioId(Long id, Long negocioId);

    // Bulk update: so atinge ofertas ativas (@SQLRestriction). Sem clearAutomatically, para nao limpar o
    // contexto de quem publicou o evento e perder alteracoes pendentes dele.
    @Modifying
    @Query("UPDATE Oferta o SET o.excluidoEm = :agora WHERE o.negocio.id = :negocioId")
    void excluirPorNegocio(Long negocioId, LocalDateTime agora);
}

