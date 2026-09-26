package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.aviso.dto.AvaliacaoRequest;
import br.edu.ufersa.pw.bairro.aviso.dto.AvaliacaoResponse;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/negocios/{negocioId}/avisos/{avisoId}/votos")
public class AvaliacaoController {

    // PUT /api/v1/negocios/{negocioId}/avisos/{avisoId}/votos/me - avalia o aviso como util/nao-util (idempotente por usuario)
    @PutMapping("/me")
    public ResponseEntity<AvaliacaoResponse> votar(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @PathVariable Long negocioId,
            @PathVariable Long avisoId,
            @RequestBody AvaliacaoRequest request) {
        return null;
    }
}
