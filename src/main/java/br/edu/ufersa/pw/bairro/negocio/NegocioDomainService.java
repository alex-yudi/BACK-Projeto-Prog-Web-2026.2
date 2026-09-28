package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.usuario.Usuario;
import org.springframework.stereotype.Service;

@Service
class NegocioDomainService {

    public void validarReivindicacao(Negocio negocio) {
        if (negocio.getDono() != null) {
            // Lança exceção de negócio para o RestControllerAdvice capturar e retornar 409 Conflict
            throw new IllegalStateException("Este negócio já possui um proprietário vinculado.");
        }
    }

    // D4: usuario comum vira dono na hora do cadastro; ADMIN cadastra sempre sem dono.
    void definirDonoNoCadastro(Negocio negocio, Usuario autenticado) {
        if (!autenticado.isAdmin()) {
            negocio.reivindicar(autenticado);
        }
    }

    // Regra de autorizacao: so o dono do negocio ou um ADMIN altera/exclui. Negocio sem dono so passa
    // para ADMIN.
    void exigirDonoOuAdmin(Usuario autenticado, Negocio negocio) {
        Long donoId = negocio.getDono() == null ? null : negocio.getDono().getId();
        autenticado.exigirDonoOuAdmin(donoId, "Somente o dono do negócio ou um ADMIN pode alterá-lo.");
    }
}