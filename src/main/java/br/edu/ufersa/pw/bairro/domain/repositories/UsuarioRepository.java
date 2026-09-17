package br.edu.ufersa.pw.bairro.domain.repositories;

import br.edu.ufersa.pw.bairro.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // POST /auth/login - busca o usuario pelo email informado
    Optional<Usuario> findByEmail(String email);

    // POST /usuarios - valida que o email ainda nao esta cadastrado
    boolean existsByEmail(String email);
}
