package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.negocio.dto.CategoriaNegocio;
import br.edu.ufersa.pw.bairro.negocio.dto.NegocioCreate;
import br.edu.ufersa.pw.bairro.negocio.dto.NegocioResponse;
import br.edu.ufersa.pw.bairro.negocio.dto.NegocioUpdate;
import br.edu.ufersa.pw.bairro.usuario.Usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/negocios")
public class NegocioController {

    private final NegocioApplicationService service;

    NegocioController(NegocioApplicationService service) {
        this.service = service;
    }

    // POST /api/v1/negocios - cadastra um novo negocio (usuario comum vira dono; ADMIN cria sem dono)
    @PostMapping
    public ResponseEntity<NegocioResponse> cadastrar(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @RequestBody NegocioCreate request,
            UriComponentsBuilder uriBuilder
    ) {
        NegocioResponse criado = service.cadastrar(usuarioAutenticado, request);
        return ResponseEntity
                .created(uriBuilder.path("/api/v1/negocios/{id}").buildAndExpand(criado.id()).toUri())
                .body(criado);
    }

    // GET /api/v1/negocios - busca/listagem, cobre o Feed e a tela de Busca
    @GetMapping
    public ResponseEntity<List<NegocioResponse>> listar(
            @RequestParam(required = false) String bairro,
            @RequestParam(required = false) CategoriaNegocio categoria,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return ResponseEntity.ok(service.listar(bairro, categoria, q, page, size));
    }

    // GET /api/v1/negocios/{id} - perfil do negocio (dono nulo = nao reivindicado)
    @GetMapping("/{id}")
    public ResponseEntity<NegocioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // PUT /api/v1/negocios/{id} - edita os dados cadastrais (somente o dono ou ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<NegocioResponse> atualizar(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @PathVariable Long id,
            @RequestBody NegocioUpdate request
    ) {
        return ResponseEntity.ok(service.atualizar(usuarioAutenticado, id, request));
    }

    // DELETE /api/v1/negocios/{id} - desativa/exclui o negocio (somente o dono ou ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @PathVariable Long id) {
        service.excluir(usuarioAutenticado, id);
        return ResponseEntity.noContent().build();
    }
}
