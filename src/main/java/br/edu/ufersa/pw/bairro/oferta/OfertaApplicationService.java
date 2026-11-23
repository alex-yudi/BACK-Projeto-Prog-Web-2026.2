package br.edu.ufersa.pw.bairro.oferta;

import br.edu.ufersa.pw.bairro.negocio.Negocio;
import br.edu.ufersa.pw.bairro.negocio.NegocioApi;
import br.edu.ufersa.pw.bairro.oferta.dto.OfertaCreate;
import br.edu.ufersa.pw.bairro.oferta.dto.OfertaResponse;
import br.edu.ufersa.pw.bairro.oferta.dto.OfertaUpdate;
import br.edu.ufersa.pw.bairro.shared.exception.EntidadeNaoEncontradaException;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class OfertaApplicationService {

    private final OfertaDomainService domainService;
    private final OfertaRepository repository;
    private final OfertaMapper mapper;
    private final NegocioApi negocioApi;
    private final EntityManager em;

    OfertaApplicationService(OfertaDomainService domainService, OfertaRepository repository, OfertaMapper mapper,
                              NegocioApi negocioApi, EntityManager em) {
        this.domainService = domainService;
        this.repository = repository;
        this.mapper = mapper;
        this.negocioApi = negocioApi;
        this.em = em;
    }

    @Transactional
    OfertaResponse criar(Usuario autenticado, Long negocioId, OfertaCreate dto) {
        exigirDonoOuAdmin(autenticado, negocioId);
        // O negocio ja foi validado pelo NegocioApi; so a referencia (FK) e necessaria, sem novo SELECT.
        Oferta oferta = mapper.toEntity(em.getReference(Negocio.class, negocioId), dto);
        return mapper.toResponse(repository.save(oferta));
    }

    @Transactional(readOnly = true)
    List<OfertaResponse> listar(Long negocioId) {
        negocioApi.buscarPorId(negocioId);
        return mapper.toResponseList(repository.findByNegocioId(negocioId));
    }

    @Transactional(readOnly = true)
    OfertaResponse buscarPorId(Long negocioId, Long ofertaId) {
        negocioApi.buscarPorId(negocioId);
        return mapper.toResponse(obter(negocioId, ofertaId));
    }

    @Transactional
    OfertaResponse atualizar(Usuario autenticado, Long negocioId, Long ofertaId, OfertaUpdate dto) {
        exigirDonoOuAdmin(autenticado, negocioId);
        Oferta oferta = obter(negocioId, ofertaId);
        mapper.atualizarEntidade(dto, oferta);
        return mapper.toResponse(oferta);
    }

    @Transactional
    void excluir(Usuario autenticado, Long negocioId, Long ofertaId) {
        exigirDonoOuAdmin(autenticado, negocioId);
        repository.delete(obter(negocioId, ofertaId));
    }

    // 404 se o negocio nao existe/foi excluido; 403 se o usuario nao e o dono (nem ADMIN).
    private void exigirDonoOuAdmin(Usuario autenticado, Long negocioId) {
        Long donoId = negocioApi.buscarPorId(negocioId).donoId();
        domainService.exigirDonoOuAdmin(autenticado, donoId);
    }

    // Filtra pelo negocio do path: oferta de outro negocio tambem e 404.
    private Oferta obter(Long negocioId, Long ofertaId) {
        return repository.findByIdAndNegocioId(ofertaId, negocioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Oferta " + ofertaId + " não encontrada."));
    }
}
