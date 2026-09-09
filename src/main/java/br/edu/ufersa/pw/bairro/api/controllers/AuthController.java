package br.edu.ufersa.pw.bairro.api.controllers;

import br.edu.ufersa.pw.bairro.api.dtos.LoginRequest;
import br.edu.ufersa.pw.bairro.api.dtos.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // POST /auth/login - autentica o usuario e devolve um token (REST e stateless, sem sessao no servidor)
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return null;
    }

    // POST /auth/logout - invalida o token do usuario autenticado
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return null;
    }
}
