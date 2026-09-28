package br.edu.ufersa.pw.bairro.oferta;

import br.edu.ufersa.pw.bairro.usuario.Usuario;
import org.springframework.stereotype.Service;

@Service
class OfertaDomainService {

    // Regra de autorizacao sobre as ofertas de um negocio: so o dono ou um ADMIN gerencia.
    void exigirDonoOuAdmin(Usuario autenticado, Long donoId) {
        autenticado.exigirDonoOuAdmin(donoId, "Somente o dono do negócio ou um ADMIN pode gerenciar as ofertas.");
    }
}
