package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.negocio.dto.ReivindicacaoRequest;
import br.edu.ufersa.pw.bairro.negocio.dto.ReivindicacaoResponse;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/negocios/{negocioId}/reivindicacoes")
public class ReivindicacaoController {

    private final NegocioApplicationService service;

    public ReivindicacaoController(NegocioApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ReivindicacaoResponse> reivindicar(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @PathVariable Long negocioId,
            @Valid @RequestBody(required = false) ReivindicacaoRequest request,
            UriComponentsBuilder uriBuilder) {

        ReivindicacaoResponse criado = service.reivindicar(usuarioAutenticado, negocioId, request);
        return ResponseEntity
                .created(uriBuilder.path("/api/v1/negocios/{negocioId}/reivindicacoes/{id}")
                        .buildAndExpand(negocioId, criado.id()).toUri())
                .body(criado);
    }
}