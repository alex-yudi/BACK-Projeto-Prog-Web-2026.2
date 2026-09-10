package br.edu.ufersa.pw.bairro.api.controllers;

import br.edu.ufersa.pw.bairro.api.dtos.oferta.OfertaCreate;
import br.edu.ufersa.pw.bairro.api.dtos.oferta.OfertaResponse;
import br.edu.ufersa.pw.bairro.api.dtos.oferta.OfertaUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/negocios/{negocioId}/ofertas")
public class OfertaController {

    // POST /negocios/{negocioId}/ofertas - cria uma oferta
    @PostMapping
    public ResponseEntity<OfertaResponse> criar(
            @PathVariable Long negocioId,
            @RequestBody OfertaCreate request) {
        return null;
    }

    // GET /negocios/{negocioId}/ofertas - lista as ofertas do negocio
    @GetMapping
    public ResponseEntity<List<OfertaResponse>> listar(
            @PathVariable Long negocioId) {
        return null;
    }

    // GET /negocios/{negocioId}/ofertas/{ofertaId} - busca uma oferta
    @GetMapping("/{ofertaId}")
    public ResponseEntity<OfertaResponse> buscarPorId(
            @PathVariable Long negocioId,
            @PathVariable Long ofertaId) {
        return null;
    }

    // PUT /negocios/{negocioId}/ofertas/{ofertaId} - edita a oferta
    @PutMapping("/{ofertaId}")
    public ResponseEntity<OfertaResponse> atualizar(
            @PathVariable Long negocioId,
            @PathVariable Long ofertaId,
            @RequestBody OfertaUpdate request) {
        return null;
    }

    // DELETE /negocios/{negocioId}/ofertas/{ofertaId} - exclui a oferta
    @DeleteMapping("/{ofertaId}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long negocioId,
            @PathVariable Long ofertaId) {
        return null;
    }
}