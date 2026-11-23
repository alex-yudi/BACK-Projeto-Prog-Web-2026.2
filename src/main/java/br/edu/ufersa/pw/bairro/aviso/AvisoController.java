package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.aviso.dto.*;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/negocios/{negocioId}/avisos")
public class AvisoController {

    private final AvisoApplicationService service;

    AvisoController(AvisoApplicationService service) {
        this.service = service;
    }

    // POST /api/v1/negocios/{negocioId}/avisos - posta um aviso sobre o negocio (autor = usuario autenticado)
    @PostMapping
    public ResponseEntity<AvisoResponse> criar(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @PathVariable Long negocioId,
            @Valid @RequestBody AvisoCreate request,
            UriComponentsBuilder uriBuilder) {
        AvisoResponse criado = service.criar(usuarioAutenticado, negocioId, request);
        return ResponseEntity
                .created(uriBuilder.path("/api/v1/negocios/{negocioId}/avisos/{avisoId}")
                        .buildAndExpand(negocioId, criado.id()).toUri())
                .body(criado);
    }

    // GET /api/v1/negocios/{negocioId}/avisos - lista os avisos do negocio (mural do perfil + dashboard do dono)
    @GetMapping
    public ResponseEntity<List<AvisoResponse>> listar(
            @PathVariable Long negocioId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return ResponseEntity.ok(service.listar(negocioId, page, size));
    }

    // PUT /api/v1/negocios/{negocioId}/avisos/{avisoId} - edita o aviso (somente o autor ou ADMIN)
    @PutMapping("/{avisoId}")
    public ResponseEntity<AvisoResponse> atualizar(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @PathVariable Long negocioId,
            @PathVariable Long avisoId,
            @Valid @RequestBody AvisoUpdate request) {
        return ResponseEntity.ok(service.atualizar(usuarioAutenticado, negocioId, avisoId, request));
    }

    // DELETE /api/v1/negocios/{negocioId}/avisos/{avisoId} - exclui o aviso (somente o autor ou ADMIN)
    @DeleteMapping("/{avisoId}")
    public ResponseEntity<Void> excluir(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @PathVariable Long negocioId,
            @PathVariable Long avisoId) {
        service.excluir(usuarioAutenticado, negocioId, avisoId);
        return ResponseEntity.noContent().build();
    }
}
