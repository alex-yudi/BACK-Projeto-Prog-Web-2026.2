package br.edu.ufersa.pw.bairro.usuario;

import org.springframework.stereotype.Service;

@Service
class UsuarioDomainService {

    private final UsuarioRepository repository;

    UsuarioDomainService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void validarEmailDisponivel(String email) {
        if (repository.existsByEmailIgnoreCase(email)) {
            // E-mail já cadastrado: interrompe o cadastro antes de salvar o usuario
            throw new IllegalArgumentException("O e-mail informado já está em uso.");
        }
    }
}