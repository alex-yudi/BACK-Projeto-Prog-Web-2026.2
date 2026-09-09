package br.edu.ufersa.pw.bairro.api.controllers;

import br.edu.ufersa.pw.bairro.api.dtos.negocio.NegocioCreate;
import br.edu.ufersa.pw.bairro.api.dtos.negocio.NegocioResponse;
import br.edu.ufersa.pw.bairro.api.dtos.negocio.NegocioUpdate;
import br.edu.ufersa.pw.bairro.api.dtos.negocio.ReivindicacaoRequest;
import br.edu.ufersa.pw.bairro.api.dtos.negocio.ReivindicacaoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/negocios")
public class NegocioController {

    // POST /negocios - cadastra um novo negocio (dono = usuario autenticado)
    @PostMapping
    public ResponseEntity<NegocioResponse> cadastrar(@RequestBody NegocioCreate request, UriComponentsBuilder uriBuilder) {
        return null;
    }

    // GET /negocios - busca/listagem, cobre o Feed e a tela de Busca
    @GetMapping
    public ResponseEntity<List<NegocioResponse>> listar(
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
    public ResponseEntity<NegocioResponse> buscarPorId(@PathVariable Long id) {
        return null;
    }

    // PUT /negocios/{id} - edita os dados cadastrais (somente o dono)
    @PutMapping("/{id}")
    public ResponseEntity<NegocioResponse> atualizar(@PathVariable Long id, @RequestBody NegocioUpdate request) {
        return null;
    }

    // DELETE /negocios/{id} - desativa/exclui o negocio (somente o dono)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        return null;
    }

    // POST /negocios/{id}/reivindicacoes - reivindica um perfil nao reivindicado
    @PostMapping("/{id}/reivindicacoes")
    public ResponseEntity<ReivindicacaoResponse> reivindicar(@PathVariable Long id, @RequestBody ReivindicacaoRequest request) {
        return null;
    }
}
