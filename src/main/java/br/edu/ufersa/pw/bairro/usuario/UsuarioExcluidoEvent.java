package br.edu.ufersa.pw.bairro.usuario;

// Publicado quando o usuario exclui a propria conta. O modulo negocio escuta com @EventListener
// e deixa os negocios dele sem dono (dono nulo), na mesma transacao.
public record UsuarioExcluidoEvent(Long usuarioId) {
}
