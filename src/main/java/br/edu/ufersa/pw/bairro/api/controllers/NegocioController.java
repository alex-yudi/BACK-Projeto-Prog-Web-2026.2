package br.edu.ufersa.pw.bairro.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/negocios")
public class NegocioController {

    // POST /negocios - cadastra um novo negocio (dono = usuario autenticado)
    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody Object request, UriComponentsBuilder uriBuilder) {
        return null;
    }

    // GET /negocios - busca/listagem, cobre o Feed e a tela de Busca
    @GetMapping
    public ResponseEntity<Object> listar(
            @RequestParam(required = false) String bairro,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return null;
    }

    // GET /negocios/{id} - perfil do negocio (dono nulo = nao reivindicado)
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
        return null;
    }

    // PUT /negocios/{id} - edita os dados cadastrais (somente o dono)
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody Object request) {
        return null;
    }

    // DELETE /negocios/{id} - desativa/exclui o negocio (somente o dono)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        return null;
    }

    // POST /negocios/{id}/reivindicacoes - reivindica um perfil nao reivindicado
    @PostMapping("/{id}/reivindicacoes")
    public ResponseEntity<Object> reivindicar(@PathVariable Long id, @RequestBody Object request) {
        return null;
    }
}
