package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.negocio.dto.CategoriaNegocio;
import br.edu.ufersa.pw.bairro.negocio.dto.NegocioCreate;
import br.edu.ufersa.pw.bairro.negocio.dto.NegocioResponse;
import br.edu.ufersa.pw.bairro.negocio.dto.NegocioUpdate;
import br.edu.ufersa.pw.bairro.shared.Paginacao;
import br.edu.ufersa.pw.bairro.shared.exception.EntidadeNaoEncontradaException;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class NegocioApplicationService {

    private final NegocioRepository repository;
    private final NegocioMapper mapper;
    private final ApplicationEventPublisher eventos;

    NegocioApplicationService(NegocioRepository repository, NegocioMapper mapper, ApplicationEventPublisher eventos) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventos = eventos;
    }

    // D4: usuario comum vira dono na hora; ADMIN cadastra sempre sem dono.
    @Transactional
    NegocioResponse cadastrar(Usuario autenticado, NegocioCreate dto) {
        Negocio negocio = mapper.toEntity(dto);
        if (!autenticado.isAdmin()) {
            negocio.reivindicar(autenticado);
        }
        return mapper.toResponse(repository.save(negocio));
    }

    @Transactional(readOnly = true)
    NegocioResponse buscarPorId(Long id) {
        return mapper.toResponse(obter(id));
    }

    @Transactional(readOnly = true)
    List<NegocioResponse> listar(String bairro, CategoriaNegocio categoria, String q, Integer page, Integer size) {
        String termo = (q == null || q.isBlank()) ? null : q.trim();
        String filtroBairro = (bairro == null || bairro.isBlank()) ? null : bairro.trim();
        return mapper.toResponseList(
                repository.buscar(filtroBairro, mapper.toDomain(categoria), termo, Paginacao.de(page, size)).getContent());
    }

    @Transactional
    NegocioResponse atualizar(Usuario autenticado, Long id, NegocioUpdate dto) {
        Negocio negocio = obter(id);
        exigirDonoOuAdmin(autenticado, negocio);
        mapper.atualizarEntidade(dto, negocio);
        return mapper.toResponse(negocio);
    }

    @Transactional
    void excluir(Usuario autenticado, Long id) {
        Negocio negocio = obter(id);
        exigirDonoOuAdmin(autenticado, negocio);
        repository.delete(negocio);
        eventos.publishEvent(new NegocioExcluidoEvent(id));
    }

    private Negocio obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Negócio " + id + " não encontrado."));
    }

    private void exigirDonoOuAdmin(Usuario autenticado, Negocio negocio) {
        Long donoId = negocio.getDono() == null ? null : negocio.getDono().getId();
        autenticado.exigirDonoOuAdmin(donoId, "Somente o dono do negócio ou um ADMIN pode alterá-lo.");
    }
}
