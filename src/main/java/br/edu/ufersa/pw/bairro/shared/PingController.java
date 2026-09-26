package br.edu.ufersa.pw.bairro.shared;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    // GET /api/ping - publica; so confirma que a API esta no ar (nao acessa o banco).
    @GetMapping("/api/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
