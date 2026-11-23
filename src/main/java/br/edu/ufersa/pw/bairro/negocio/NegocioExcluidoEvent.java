package br.edu.ufersa.pw.bairro.negocio;

// Publicado quando um negocio e excluido (soft delete). Os modulos aviso e oferta escutam com
// @EventListener e marcam os registros do negocio como excluidos, na mesma transacao.
public record NegocioExcluidoEvent(Long negocioId) {
}
