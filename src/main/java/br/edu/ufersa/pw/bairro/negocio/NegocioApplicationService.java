package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.negocio.dto.CategoriaNegocio;
import br.edu.ufersa.pw.bairro.negocio.dto.NegocioCreate;
import br.edu.ufersa.pw.bairro.negocio.dto.NegocioResponse;
import br.edu.ufersa.pw.bairro.negocio.dto.NegocioUpdate;
import br.edu.ufersa.pw.bairro.negocio.dto.ReivindicacaoRequest;
import br.edu.ufersa.pw.bairro.negocio.dto.ReivindicacaoResponse;
import br.edu.ufersa.pw.bairro.shared.Paginacao;
import br.edu.ufersa.pw.bairro.shared.exception.EntidadeNaoEncontradaException;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
class NegocioApplicationService {

    private final NegocioDomainService domainService;
    private final NegocioRepository repository;
    private final ReivindicacaoRepository reivindicacaoRepository;
    private final NegocioMapper mapper;
    private final ApplicationEventPublisher eventos;

    NegocioApplicationService(NegocioDomainService domainService, NegocioRepository repository,
                               ReivindicacaoRepository reivindicacaoRepository, NegocioMapper mapper,
                               ApplicationEventPublisher eventos) {
        this.domainService = domainService;
        this.repository = repository;
        this.reivindicacaoRepository = reivindicacaoRepository;
        this.mapper = mapper;
        this.eventos = eventos;
    }

    @Transactional
    NegocioResponse cadastrar(Usuario autenticado, NegocioCreate dto) {
        Negocio negocio = mapper.toEntity(dto);
        domainService.definirDonoNoCadastro(negocio, autenticado);
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
        domainService.exigirDonoOuAdmin(autenticado, negocio);
        mapper.atualizarEntidade(dto, negocio);
        return mapper.toResponse(negocio);
    }

    @Transactional
    void excluir(Usuario autenticado, Long id) {
        Negocio negocio = obter(id);
        domainService.exigirDonoOuAdmin(autenticado, negocio);
        repository.delete(negocio);
        eventos.publishEvent(new NegocioExcluidoEvent(id));
    }

    // Solicita a reivindicacao de um negocio sem dono (R3). So vira dono de fato quando um ADMIN aprovar (R5).
    @Transactional
    ReivindicacaoResponse reivindicar(Usuario autenticado, Long negocioId, ReivindicacaoRequest request) {
        Negocio negocio = obter(negocioId);
        domainService.validarReivindicacao(negocio);

        Reivindicacao reivindicacao = new Reivindicacao();
        reivindicacao.setNegocio(negocio);
        reivindicacao.setUsuario(autenticado);
        reivindicacao.setJustificativa(request != null ? request.justificativa() : null);
        reivindicacao.setCriadoEm(LocalDateTime.now());

        Reivindicacao salva = reivindicacaoRepository.save(reivindicacao);
        return new ReivindicacaoResponse(
                salva.getId(), salva.getNegocio().getId(), salva.getUsuario().getId(),
                salva.getJustificativa(), salva.getCriadoEm());
    }

    private Negocio obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Negócio " + id + " não encontrado."));
    }
}
