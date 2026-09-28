package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.negocio.dto.CategoriaNegocio;
import br.edu.ufersa.pw.bairro.negocio.dto.DecisaoReivindicacao;
import br.edu.ufersa.pw.bairro.negocio.dto.NegocioCreate;
import br.edu.ufersa.pw.bairro.negocio.dto.NegocioResponse;
import br.edu.ufersa.pw.bairro.negocio.dto.NegocioUpdate;
import br.edu.ufersa.pw.bairro.negocio.dto.ReivindicacaoDecisao;
import br.edu.ufersa.pw.bairro.negocio.dto.ReivindicacaoRequest;
import br.edu.ufersa.pw.bairro.negocio.dto.ReivindicacaoResponse;
import br.edu.ufersa.pw.bairro.shared.Paginacao;
import br.edu.ufersa.pw.bairro.shared.exception.EntidadeNaoEncontradaException;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class NegocioApplicationService {

    private final NegocioDomainService domainService;
    private final NegocioRepository repository;
    private final ReivindicacaoRepository reivindicacaoRepository;
    private final NegocioMapper mapper;
    private final ReivindicacaoMapper reivindicacaoMapper;
    private final ApplicationEventPublisher eventos;

    NegocioApplicationService(NegocioDomainService domainService, NegocioRepository repository,
            ReivindicacaoRepository reivindicacaoRepository, NegocioMapper mapper,
            ReivindicacaoMapper reivindicacaoMapper, ApplicationEventPublisher eventos) {
        this.domainService = domainService;
        this.repository = repository;
        this.reivindicacaoRepository = reivindicacaoRepository;
        this.mapper = mapper;
        this.reivindicacaoMapper = reivindicacaoMapper;
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
                repository.buscar(filtroBairro, mapper.toDomain(categoria), termo, Paginacao.de(page, size))
                        .getContent());
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

    // Solicita a reivindicacao de um negocio sem dono. So vira dono de fato
    // quando um ADMIN aprovar.
    @Transactional
    ReivindicacaoResponse reivindicar(Usuario autenticado, Long negocioId, ReivindicacaoRequest request) {
        Negocio negocio = obter(negocioId);
        domainService.validarReivindicacao(negocio);

        String justificativa = request != null ? request.justificativa() : null;
        Reivindicacao salva = reivindicacaoRepository.save(new Reivindicacao(negocio, autenticado, justificativa));
        return reivindicacaoMapper.toResponse(salva);
    }

    // ADMIN decide uma reivindicacao pendente. Aprovar torna o solicitante dono do
    // negocio e
    // rejeita automaticamente as demais pendentes do mesmo negocio; rejeitar
    // so marca esta.
    // 404 se a reivindicacao ou o negocio nao existem/foram excluidos; 409 em
    // corrida (@Version do Negocio
    // ou reivindicacao ja decidida por outra requisicao).
    @Transactional
    ReivindicacaoResponse decidir(Usuario admin, Long reivindicacaoId, ReivindicacaoDecisao dto) {
        domainService.exigirAdmin(admin);
        Reivindicacao reivindicacao = reivindicacaoRepository.findById(reivindicacaoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Reivindicação " + reivindicacaoId + " não encontrada."));
        Negocio negocio = obter(reivindicacao.getNegocio().getId());

        if (dto.status() == DecisaoReivindicacao.APROVADA) {
            reivindicacao.aprovar(admin);
            negocio.reivindicar(reivindicacao.getUsuario());
            for (Reivindicacao pendente : reivindicacaoRepository.findByNegocioIdAndStatusAndIdNot(
                    negocio.getId(), StatusReivindicacao.PENDENTE, reivindicacao.getId())) {
                pendente.rejeitar(admin);
            }
        } else {
            reivindicacao.rejeitar(admin);
        }
        return reivindicacaoMapper.toResponse(reivindicacao);
    }

    // Tela do ADMIN: lista as reivindicacoes, com filtro opcional por status (nulo = todas).
    @Transactional(readOnly = true)
    List<ReivindicacaoResponse> listarReivindicacoes(Usuario admin, br.edu.ufersa.pw.bairro.negocio.dto.StatusReivindicacao statusDto,
            Integer page, Integer size) {
        domainService.exigirAdmin(admin);
        StatusReivindicacao status = statusDto == null ? null : reivindicacaoMapper.toDomain(statusDto);
        return reivindicacaoMapper.toResponseList(
                reivindicacaoRepository.buscar(status, Paginacao.de(page, size)).getContent());
    }

    private Negocio obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Negócio " + id + " não encontrado."));
    }
}
