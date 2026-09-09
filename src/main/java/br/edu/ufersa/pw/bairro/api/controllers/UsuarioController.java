package br.edu.ufersa.pw.bairro.api.controllers;

import br.edu.ufersa.pw.bairro.api.dtos.aviso.AvisoResponse;
import br.edu.ufersa.pw.bairro.api.dtos.usuario.UsuarioAtualizacaoRequest;
import br.edu.ufersa.pw.bairro.api.dtos.usuario.UsuarioRegistroRequest;
import br.edu.ufersa.pw.bairro.api.dtos.usuario.UsuarioResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    // POST /usuarios - cria uma nova conta de usuario (tela "Criar sua conta")
    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody UsuarioRegistroRequest request, UriComponentsBuilder uriBuilder) {
        return null;
    }

    // GET /usuarios/me - busca os dados do usuario autenticado (tela "Minha conta")
    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> buscarPerfil() {
        return null;
    }

    // PUT /usuarios/me - atualiza os dados do usuario autenticado
    @PutMapping("/me")
    public ResponseEntity<UsuarioResponse> atualizarPerfil(@RequestBody UsuarioAtualizacaoRequest request) {
        return null;
    }

    // DELETE /usuarios/me - exclui a conta do usuario autenticado
    @DeleteMapping("/me")
    public ResponseEntity<Void> excluirPerfil() {
        return null;
    }

    // GET /usuarios/me/avisos - lista os avisos postados pelo usuario autenticado (tela "Meus avisos")
    @GetMapping("/me/avisos")
    public ResponseEntity<List<AvisoResponse>> listarMeusAvisos() {
        return null;
    }
}