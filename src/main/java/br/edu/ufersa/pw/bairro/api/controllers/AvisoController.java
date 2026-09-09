package br.edu.ufersa.pw.bairro.api.controllers;

import br.edu.ufersa.pw.bairro.api.dtos.aviso.AvaliacaoRequest;
import br.edu.ufersa.pw.bairro.api.dtos.aviso.AvaliacaoResponse;
import br.edu.ufersa.pw.bairro.api.dtos.aviso.AvisoCreate;
import br.edu.ufersa.pw.bairro.api.dtos.aviso.AvisoResponse;
import br.edu.ufersa.pw.bairro.api.dtos.aviso.AvisoUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/negocios/{negocioId}/avisos")
public class AvisoController {

    // POST /negocios/{negocioId}/avisos - posta um aviso sobre o negocio
    @PostMapping
    public ResponseEntity<AvisoResponse> criar(@PathVariable Long negocioId, @RequestBody AvisoCreate request) {
        return null;
    }

    // GET /negocios/{negocioId}/avisos - lista os avisos do negocio (mural do perfil + dashboard do dono)
    @GetMapping
    public ResponseEntity<List<AvisoResponse>> listar(
            @PathVariable Long negocioId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return null;
    }

    // PUT /negocios/{negocioId}/avisos/{avisoId} - edita o aviso (somente o autor)
    @PutMapping("/{avisoId}")
    public ResponseEntity<AvisoResponse> atualizar(@PathVariable Long negocioId, @PathVariable Long avisoId, @RequestBody AvisoUpdate request) {
        return null;
    }

    // DELETE /negocios/{negocioId}/avisos/{avisoId} - exclui o aviso (somente o autor)
    @DeleteMapping("/{avisoId}")
    public ResponseEntity<Void> excluir(@PathVariable Long negocioId, @PathVariable Long avisoId) {
        return null;
    }

    // PUT /negocios/{negocioId}/avisos/{avisoId}/votos/me - avalia o aviso como util/nao-util (idempotente por usuario)
    @PutMapping("/{avisoId}/votos/me")
    public ResponseEntity<AvaliacaoResponse> votar(@PathVariable Long negocioId, @PathVariable Long avisoId, @RequestBody AvaliacaoRequest request) {
        return null;
    }
}
