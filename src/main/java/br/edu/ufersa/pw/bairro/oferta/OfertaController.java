package br.edu.ufersa.pw.bairro.oferta;

import br.edu.ufersa.pw.bairro.oferta.dto.OfertaCreate;
import br.edu.ufersa.pw.bairro.oferta.dto.OfertaResponse;
import br.edu.ufersa.pw.bairro.oferta.dto.OfertaUpdate;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/negocios/{negocioId}/ofertas")
public class OfertaController {

    private final OfertaApplicationService service;

    OfertaController(OfertaApplicationService service) {
        this.service = service;
    }

    // POST /api/v1/negocios/{negocioId}/ofertas - cria uma oferta (somente o dono do negocio ou ADMIN)
    @PostMapping
    public ResponseEntity<OfertaResponse> criar(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @PathVariable Long negocioId,
            @RequestBody OfertaCreate request,
            UriComponentsBuilder uriBuilder) {
        OfertaResponse criada = service.criar(usuarioAutenticado, negocioId, request);
        return ResponseEntity
                .created(uriBuilder.path("/api/v1/negocios/{negocioId}/ofertas/{ofertaId}")
                        .buildAndExpand(negocioId, criada.id()).toUri())
                .body(criada);
    }

    // GET /api/v1/negocios/{negocioId}/ofertas - lista as ofertas do negocio
    @GetMapping
    public ResponseEntity<List<OfertaResponse>> listar(
            @PathVariable Long negocioId) {
        return ResponseEntity.ok(service.listar(negocioId));
    }

    // GET /api/v1/negocios/{negocioId}/ofertas/{ofertaId} - busca uma oferta
    @GetMapping("/{ofertaId}")
    public ResponseEntity<OfertaResponse> buscarPorId(
            @PathVariable Long negocioId,
            @PathVariable Long ofertaId) {
        return ResponseEntity.ok(service.buscarPorId(negocioId, ofertaId));
    }

    // PUT /api/v1/negocios/{negocioId}/ofertas/{ofertaId} - edita a oferta (somente o dono do negocio ou ADMIN)
    @PutMapping("/{ofertaId}")
    public ResponseEntity<OfertaResponse> atualizar(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @PathVariable Long negocioId,
            @PathVariable Long ofertaId,
            @RequestBody OfertaUpdate request) {
        return ResponseEntity.ok(service.atualizar(usuarioAutenticado, negocioId, ofertaId, request));
    }

    // DELETE /api/v1/negocios/{negocioId}/ofertas/{ofertaId} - exclui a oferta (somente o dono do negocio ou ADMIN)
    @DeleteMapping("/{ofertaId}")
    public ResponseEntity<Void> excluir(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @PathVariable Long negocioId,
            @PathVariable Long ofertaId) {
        service.excluir(usuarioAutenticado, negocioId, ofertaId);
        return ResponseEntity.noContent().build();
    }
}
