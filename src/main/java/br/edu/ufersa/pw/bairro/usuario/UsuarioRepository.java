package br.edu.ufersa.pw.bairro.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // POST /api/v1/auth/login - busca o usuario pelo email informado
    Optional<Usuario> findByEmail(String email);

    // POST /api/v1/usuarios - valida que o email ainda nao esta cadastrado
    boolean existsByEmail(String email);
}
