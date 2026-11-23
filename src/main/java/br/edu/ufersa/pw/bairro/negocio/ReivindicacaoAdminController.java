package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.negocio.dto.ReivindicacaoDecisao;
import br.edu.ufersa.pw.bairro.negocio.dto.ReivindicacaoResponse;
import br.edu.ufersa.pw.bairro.negocio.dto.StatusReivindicacao;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Tela do ADMIN para revisar e decidir reivindicacoes de negocio. Acesso restrito pelo
// hasRole("ADMIN") no SecurityConfig.
@RestController
@RequestMapping("/api/v1/reivindicacoes")
public class ReivindicacaoAdminController {

    private final NegocioApplicationService service;

    ReivindicacaoAdminController(NegocioApplicationService service) {
        this.service = service;
    }

    // GET /api/v1/reivindicacoes?status=PENDENTE - lista paginada; status omitido lista todas
    @GetMapping
    public ResponseEntity<List<ReivindicacaoResponse>> listar(
            @AuthenticationPrincipal Usuario admin,
            @RequestParam(required = false) StatusReivindicacao status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(service.listarReivindicacoes(admin, status, page, size));
    }

    // PATCH /api/v1/reivindicacoes/{id} - aprova ou rejeita
    @PatchMapping("/{id}")
    public ResponseEntity<ReivindicacaoResponse> decidir(
            @AuthenticationPrincipal Usuario admin,
            @PathVariable Long id,
            @Valid @RequestBody ReivindicacaoDecisao request) {
        return ResponseEntity.ok(service.decidir(admin, id, request));
    }
}
