package br.edu.ufersa.pw.bairro.negocio;

import org.springframework.data.jpa.repository.JpaRepository;

// Package-private para garantir o encapsulamento da feature
interface ReivindicacaoRepository extends JpaRepository<Reivindicacao, Long> {
}