package br.edu.ufersa.pw.bairro.negocio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Package-private para garantir o encapsulamento da feature
interface ReivindicacaoRepository extends JpaRepository<Reivindicacao, Long> {

    // ao aprovar uma reinvidicacao, as demais pendentes do mesmo negocio sao
    // rejeitadas automaticamente.
    List<Reivindicacao> findByNegocioIdAndStatusAndIdNot(Long negocioId, StatusReivindicacao status, Long id);

    // Tela do ADMIN: lista todas as reivindicacoes, com filtro opcional por status (nulo = todas).
    @Query("SELECT r FROM Reivindicacao r WHERE (:status IS NULL OR r.status = :status)")
    Page<Reivindicacao> buscar(StatusReivindicacao status, Pageable pageable);
}
