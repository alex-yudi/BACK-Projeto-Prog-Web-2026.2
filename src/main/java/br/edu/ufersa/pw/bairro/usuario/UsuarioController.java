package br.edu.ufersa.pw.bairro.usuario;

import br.edu.ufersa.pw.bairro.aviso.dto.AvisoResponse;
import br.edu.ufersa.pw.bairro.usuario.dto.UsuarioAtualizacaoRequest;
import br.edu.ufersa.pw.bairro.usuario.dto.UsuarioRegistroRequest;
import br.edu.ufersa.pw.bairro.usuario.dto.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioApplicationService service;

    public UsuarioController(UsuarioApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(
            @Valid @RequestBody UsuarioRegistroRequest request,
            UriComponentsBuilder uriBuilder) {

        UsuarioResponse criado = service.cadastrar(request);
        return ResponseEntity
                .created(uriBuilder.path("/api/v1/usuarios/{id}").buildAndExpand(criado.id()).toUri())
                .body(criado);
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> buscarPerfil(
            @AuthenticationPrincipal Usuario usuarioAutenticado) {

        return ResponseEntity.ok(service.buscarPerfil(usuarioAutenticado));
    }

    @PutMapping("/me")
    public ResponseEntity<UsuarioResponse> atualizarPerfil(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @Valid @RequestBody UsuarioAtualizacaoRequest request) {

        return ResponseEntity.ok(service.atualizarPerfil(usuarioAutenticado, request));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> excluirPerfil(
            @AuthenticationPrincipal Usuario usuarioAutenticado) {

        service.excluirPerfil(usuarioAutenticado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me/avisos")
    public ResponseEntity<List<AvisoResponse>> listarMeusAvisos(
            @AuthenticationPrincipal Usuario usuarioAutenticado,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        return ResponseEntity.ok(service.listarAvisosDoUsuario(usuarioAutenticado, page, size));
    }
}