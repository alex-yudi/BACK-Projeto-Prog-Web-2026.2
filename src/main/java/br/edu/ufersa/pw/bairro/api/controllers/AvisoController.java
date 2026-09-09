package br.edu.ufersa.pw.bairro.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/negocios/{negocioId}/avisos")
public class AvisoController {

    // POST /negocios/{negocioId}/avisos - posta um aviso sobre o negocio
    @PostMapping
    public ResponseEntity<Object> criar(@PathVariable Long negocioId, @RequestBody Object request) {
        return null;
    }

    // GET /negocios/{negocioId}/avisos - lista os avisos do negocio (mural do perfil + dashboard do dono)
    @GetMapping
    public ResponseEntity<Object> listar(
            @PathVariable Long negocioId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return null;
    }

    // PUT /negocios/{negocioId}/avisos/{avisoId} - edita o aviso (somente o autor)
    @PutMapping("/{avisoId}")
    public ResponseEntity<Object> atualizar(@PathVariable Long negocioId, @PathVariable Long avisoId, @RequestBody Object request) {
        return null;
    }

    // DELETE /negocios/{negocioId}/avisos/{avisoId} - exclui o aviso (somente o autor)
    @DeleteMapping("/{avisoId}")
    public ResponseEntity<Void> excluir(@PathVariable Long negocioId, @PathVariable Long avisoId) {
        return null;
    }

    // PUT /negocios/{negocioId}/avisos/{avisoId}/votos/me - avalia o aviso como util/nao-util (idempotente por usuario)
    @PutMapping("/{avisoId}/votos/me")
    public ResponseEntity<Object> votar(@PathVariable Long negocioId, @PathVariable Long avisoId, @RequestBody Object request) {
        return null;
    }
}
