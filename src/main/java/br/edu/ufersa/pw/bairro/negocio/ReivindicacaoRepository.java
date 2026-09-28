package br.edu.ufersa.pw.bairro.negocio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Package-private para garantir o encapsulamento da feature
interface ReivindicacaoRepository extends JpaRepository<Reivindicacao, Long> {

    // ao aprovar uma reinvidicacao, as demais pendentes do mesmo negocio sao
    // rejeitadas automaticamente.
    List<Reivindicacao> findByNegocioIdAndStatusAndIdNot(Long negocioId, StatusReivindicacao status, Long id);
}
