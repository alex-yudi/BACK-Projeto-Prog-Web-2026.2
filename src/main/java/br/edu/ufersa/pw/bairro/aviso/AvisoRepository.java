package br.edu.ufersa.pw.bairro.api.feature.aviso;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface AvisoRepository extends JpaRepository<Aviso, Long> {

    // GET /negocios/{negocioId}/avisos - ja paginado (page/size vem da query string)
    Page<Aviso> findByNegocioId(Long negocioId, Pageable pageable);

    // PUT/DELETE /negocios/{negocioId}/avisos/{avisoId} - confirma que o aviso e
    // realmente do negocio do path, nao so que o id existe.
    Optional<Aviso> findByIdAndNegocioId(Long id, Long negocioId);

    // GET /usuarios/me/avisos - tela "Meus avisos"
    Page<Aviso> findByAutorId(Long autorId, Pageable pageable);
}
