package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.usuario.Usuario;
import org.springframework.stereotype.Service;

@Service
class AvisoDomainService {

    // Regra de autorizacao sobre o aviso: so o autor original ou um ADMIN altera/exclui.
    void exigirAutorOuAdmin(Usuario autenticado, Aviso aviso) {
        autenticado.exigirDonoOuAdmin(aviso.getAutor().getId(), "Somente o autor do aviso ou um ADMIN pode alterá-lo.");
    }
}
