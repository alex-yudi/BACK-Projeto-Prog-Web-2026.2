package br.edu.ufersa.pw.bairro.usuario;

import br.edu.ufersa.pw.bairro.shared.exception.OperacaoInvalidaException;
import br.edu.ufersa.pw.bairro.usuario.dto.UsuarioRegistroRequest;
import br.edu.ufersa.pw.bairro.usuario.dto.UsuarioResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class UsuarioApplicationService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    UsuarioApplicationService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    UsuarioResponse cadastrar(UsuarioRegistroRequest dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new OperacaoInvalidaException("E-mail já cadastrado no sistema.");
        }
        String senhaCodificada = passwordEncoder.encode(dto.senha());
        Usuario salvo = repository.save(
                new Usuario(dto.nome(), dto.email(), senhaCodificada, dto.cep(), dto.numero()));
        return new UsuarioResponse(
                salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getCep(), salvo.getNumero());
    }
}
