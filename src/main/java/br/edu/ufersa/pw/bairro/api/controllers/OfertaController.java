package br.edu.ufersa.pw.bairro.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/negocios/{negocioId}/ofertas")
public class OfertaController {

    // POST /negocios/{negocioId}/ofertas - cria uma oferta (promocao com validade, ou item de catalogo sem validade)
    @PostMapping
    public ResponseEntity<Object> criar(@PathVariable Long negocioId, @RequestBody Object request) {
        return null;
    }

    // GET /negocios/{negocioId}/ofertas - lista as ofertas do negocio (promocoes ativas + catalogo)
    @GetMapping
    public ResponseEntity<Object> listar(@PathVariable Long negocioId) {
        return null;
    }

    // GET /negocios/{negocioId}/ofertas/{ofertaId}
    @GetMapping("/{ofertaId}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long negocioId, @PathVariable Long ofertaId) {
        return null;
    }

    // PUT /negocios/{negocioId}/ofertas/{ofertaId} - edita a oferta (somente o dono do negocio)
    @PutMapping("/{ofertaId}")
    public ResponseEntity<Object> atualizar(@PathVariable Long negocioId, @PathVariable Long ofertaId, @RequestBody Object request) {
        return null;
    }

    // DELETE /negocios/{negocioId}/ofertas/{ofertaId} - encerra/exclui a oferta (somente o dono do negocio)
    @DeleteMapping("/{ofertaId}")
    public ResponseEntity<Void> encerrar(@PathVariable Long negocioId, @PathVariable Long ofertaId) {
        return null;
    }
}
