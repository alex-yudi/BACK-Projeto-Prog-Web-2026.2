package br.edu.ufersa.pw.bairro.api.controllers;

import br.edu.ufersa.pw.bairro.api.dto.UsuarioAtualizacaoRequest;
import br.edu.ufersa.pw.bairro.api.dto.UsuarioRegistroRequest;
import br.edu.ufersa.pw.bairro.api.models.Aviso;
import br.edu.ufersa.pw.bairro.api.models.Usuario;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    // POST /usuarios - cria uma nova conta de usuario (tela "Criar sua conta")
    @PostMapping
    public Usuario cadastrar(@RequestBody UsuarioRegistroRequest request) {
        return null;
    }

    // GET /usuarios/me - busca os dados do usuario autenticado (tela "Minha conta")
    @GetMapping("/me")
    public Usuario buscarPerfil() {
        return null;
    }

    // PUT /usuarios/me - atualiza os dados do usuario autenticado
    @PutMapping("/me")
    public Usuario atualizarPerfil(@RequestBody UsuarioAtualizacaoRequest request) {
        return null;
    }

    // DELETE /usuarios/me - exclui a conta do usuario autenticado
    @DeleteMapping("/me")
    public void excluirPerfil() {
    }

    // GET /usuarios/me/avisos - lista os avisos postados pelo usuario autenticado (tela "Meus avisos")
    @GetMapping("/me/avisos")
    public List<Aviso> listarMeusAvisos() {
        return null;
    }
}