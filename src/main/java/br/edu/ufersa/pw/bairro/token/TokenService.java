package br.edu.ufersa.pw.bairro.token;

import br.edu.ufersa.pw.bairro.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Value("${api.security.token.secret:chave-secreta-de-prog-web-007}")
    String secret;
    private String generateToken(Usuario usu){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("bairro")
                .withSubject(usu.getEmail())
                .withClaim("role", usu.getRole().name())
                .withExpiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                .sign(algorithm);

    }

}
