package br.edu.ufersa.pw.bairro.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Email ignora maiusculas/minusculas: o Postgres, ao contrario do MySQL, diferencia a caixa.

    // POST /api/v1/auth/login - busca o usuario pelo email informado
    Optional<Usuario> findByEmailIgnoreCase(String email);

    // POST /api/v1/usuarios - valida que o email ainda nao esta cadastrado
    boolean existsByEmailIgnoreCase(String email);
}
