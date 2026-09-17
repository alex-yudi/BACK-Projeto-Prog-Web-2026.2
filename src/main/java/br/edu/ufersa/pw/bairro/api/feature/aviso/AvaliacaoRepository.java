package br.edu.ufersa.pw.bairro.api.feature.aviso;



import br.edu.ufersa.pw.bairro.domain.entities.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    // PUT .../votos/me e idempotente: busca se ja existe avaliacao desse usuario
    // pra esse aviso antes de decidir entre criar uma nova ou atualizar a existente.
    Optional<Avaliacao> findByAvisoIdAndUsuarioId(Long avisoId, Long usuarioId);

    // Usados pra montar votosUtil/votosNaoUtil no AvisoResponse.
    long countByAvisoIdAndUtilTrue(Long avisoId);

    long countByAvisoIdAndUtilFalse(Long avisoId);
}
