package br.edu.ufersa.pw.bairro.auth;

import br.edu.ufersa.pw.bairro.auth.dto.LoginRequest;
import br.edu.ufersa.pw.bairro.auth.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    // POST /api/v1/auth/login - autentica o usuario e devolve um token (REST e stateless, sem sessao no servidor)
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return null;
    }

    // POST /api/v1/auth/logout - invalida o token do usuario autenticado
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return null;
    }
}
