package br.edu.ufersa.pw.bairro.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // POST /auth/login - busca o usuario pelo email informado
    Optional<Usuario> findByEmail(String email);

    // POST /usuarios - valida que o email ainda nao esta cadastrado
    boolean existsByEmail(String email);
}
