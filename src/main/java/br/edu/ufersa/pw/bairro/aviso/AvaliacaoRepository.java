package br.edu.ufersa.pw.bairro.aviso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    // PUT .../votos/me e idempotente: busca se ja existe avaliacao desse usuario
    // pra esse aviso antes de decidir entre criar uma nova ou atualizar a existente.
    Optional<Avaliacao> findByAvisoIdAndUsuarioId(Long avisoId, Long usuarioId);

    // Usados pra montar votosUtil/votosNaoUtil no AvisoResponse.
    long countByAvisoIdAndUtilTrue(Long avisoId);

    long countByAvisoIdAndUtilFalse(Long avisoId);

    // Bulk update: os votos do usuario excluido saem da contagem (D3), sem perder o dado.
    @Modifying
    @Query("UPDATE Avaliacao a SET a.excluidoEm = :agora WHERE a.usuario.id = :usuarioId")
    void excluirPorUsuario(Long usuarioId, LocalDateTime agora);
}
