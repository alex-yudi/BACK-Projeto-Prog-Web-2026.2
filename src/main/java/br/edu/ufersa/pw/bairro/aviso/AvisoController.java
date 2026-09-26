package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.aviso.dto.*;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/negocios/{negocioId}/avisos")
public class AvisoController {

    // POST /api/v1/negocios/{negocioId}/avisos - posta um aviso sobre o negocio (autor = usuario autenticado)
    @PostMapping
    public ResponseEntity<AvisoResponse> criar(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @PathVariable Long negocioId,
            @RequestBody AvisoCreate request) {
        return null;
    }

    // GET /api/v1/negocios/{negocioId}/avisos - lista os avisos do negocio (mural do perfil + dashboard do dono)
    @GetMapping
    public ResponseEntity<List<AvisoResponse>> listar(
            @PathVariable Long negocioId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return null;
    }

    // PUT /api/v1/negocios/{negocioId}/avisos/{avisoId} - edita o aviso (somente o autor ou ADMIN)
    @PutMapping("/{avisoId}")
    public ResponseEntity<AvisoResponse> atualizar(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @PathVariable Long negocioId,
            @PathVariable Long avisoId,
            @RequestBody AvisoUpdate request) {
        return null;
    }

    // DELETE /api/v1/negocios/{negocioId}/avisos/{avisoId} - exclui o aviso (somente o autor ou ADMIN)
    @DeleteMapping("/{avisoId}")
    public ResponseEntity<Void> excluir(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @PathVariable Long negocioId,
            @PathVariable Long avisoId) {
        return null;
    }

    // PUT /api/v1/negocios/{negocioId}/avisos/{avisoId}/votos/me - avalia o aviso como util/nao-util (idempotente por usuario)
    @PutMapping("/{avisoId}/votos/me")
    public ResponseEntity<AvaliacaoResponse> votar(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @PathVariable Long negocioId,
            @PathVariable Long avisoId,
            @RequestBody AvaliacaoRequest request) {
        return null;
    }
}
