package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.negocio.dto.NegocioResponse;
import br.edu.ufersa.pw.bairro.shared.exception.EntidadeNaoEncontradaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Package-private: os outros modulos so enxergam a interface NegocioApi.
@Service
class NegocioApiImpl implements NegocioApi {

    private final NegocioRepository repository;
    private final NegocioMapper mapper;

    NegocioApiImpl(NegocioRepository repository, NegocioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public NegocioResponse buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Negócio " + id + " não encontrado."));
    }
}
