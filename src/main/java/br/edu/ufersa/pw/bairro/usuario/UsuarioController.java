package br.edu.ufersa.pw.bairro.usuario;

import br.edu.ufersa.pw.bairro.aviso.dto.AvisoResponse;
import br.edu.ufersa.pw.bairro.usuario.dto.UsuarioAtualizacaoRequest;
import br.edu.ufersa.pw.bairro.usuario.dto.UsuarioRegistroRequest;
import br.edu.ufersa.pw.bairro.usuario.dto.UsuarioResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioApplicationService service;

    public UsuarioController(UsuarioApplicationService service) {
        this.service = service;
    }

    // POST /api/v1/usuarios - cria uma nova conta de usuario (tela "Criar sua conta")
    // O recurso do usuario e exposto como /usuarios/me (nao ha /usuarios/{id}), entao o Location aponta pra la.
    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody UsuarioRegistroRequest request, UriComponentsBuilder uriBuilder) {
        UsuarioResponse salvo = service.cadastrar(request);
        URI uri = uriBuilder.path("/api/v1/usuarios/me").build().toUri();
        return ResponseEntity.created(uri).body(salvo);
    }

    // GET /api/v1/usuarios/me - busca os dados do usuario autenticado (tela "Minha conta")
    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> buscarPerfil(@AuthenticationPrincipal Usuario usuarioAutenticado) {
        return null;
    }

    // PUT /api/v1/usuarios/me - atualiza os dados do usuario autenticado
    @PutMapping("/me")
    public ResponseEntity<UsuarioResponse> atualizarPerfil(@AuthenticationPrincipal Usuario usuarioAutenticado, @RequestBody UsuarioAtualizacaoRequest request) {
        return null;
    }

    // DELETE /api/v1/usuarios/me - exclui a conta do usuario autenticado
    @DeleteMapping("/me")
    public ResponseEntity<Void> excluirPerfil(@AuthenticationPrincipal Usuario usuarioAutenticado) {
        return null;
    }

    // GET /api/v1/usuarios/me/avisos - lista os avisos postados pelo usuario autenticado (tela "Meus avisos")
    @GetMapping("/me/avisos")
    public ResponseEntity<List<AvisoResponse>> listarMeusAvisos(@AuthenticationPrincipal Usuario usuarioAutenticado) {
        return null;
    }
}