package br.edu.ufersa.pw.bairro.auth;

import br.edu.ufersa.pw.bairro.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    private static final String ISSUER = "bairro";

    @Value("${api.security.token.secret:chave-secreta-de-prog-web-007}")
    private String secret;

    public String generateToken(Usuario usu){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(usu.getEmail())
                .withClaim("role", usu.getRole().name())
                .withExpiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                .sign(algorithm);

    }

    // Devolve o subject (e-mail) se o token for valido; null se a verificacao for negada.
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

}
