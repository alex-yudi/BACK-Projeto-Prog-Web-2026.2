package br.edu.ufersa.pw.bairro.api.controllers;

import br.edu.ufersa.pw.bairro.api.dto.LoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // POST /auth/login - autentica o usuario e inicia a sessao
    @PostMapping("/login")
    public Usuario login(@RequestBody LoginRequest request) {
        return null;
    }

    // POST /auth/logout - encerra a sessao do usuario autenticado
    @PostMapping("/logout")
    public void logout() {
    }
}
