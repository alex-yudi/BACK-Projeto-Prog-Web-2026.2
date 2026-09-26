package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.negocio.dto.ReivindicacaoRequest;
import br.edu.ufersa.pw.bairro.negocio.dto.ReivindicacaoResponse;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/negocios/{negocioId}/reivindicacoes")
public class ReivindicacaoController {

    // POST /api/v1/negocios/{negocioId}/reivindicacoes - reivindica um perfil nao reivindicado
    @PostMapping
    public ResponseEntity<ReivindicacaoResponse> reivindicar(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @PathVariable Long negocioId,
            @RequestBody ReivindicacaoRequest request
    ) {
        return null;
    }
}
