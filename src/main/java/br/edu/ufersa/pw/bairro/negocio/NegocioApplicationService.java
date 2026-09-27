package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.negocio.dto.ReivindicacaoRequest;
import br.edu.ufersa.pw.bairro.negocio.dto.ReivindicacaoResponse;
import br.edu.ufersa.pw.bairro.shared.exception.EntidadeNaoEncontradaException;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
class NegocioApplicationService {

    private final NegocioDomainService domainService;
    private final NegocioRepository negocioRepository;
    private final ReivindicacaoRepository reivindicacaoRepository;

    NegocioApplicationService(NegocioDomainService domainService,
                              NegocioRepository negocioRepository,
                              ReivindicacaoRepository reivindicacaoRepository) {
        this.domainService = domainService;
        this.negocioRepository = negocioRepository;
        this.reivindicacaoRepository = reivindicacaoRepository;
    }

    @Transactional
    public ReivindicacaoResponse reivindicar(Usuario usuarioAutenticado, Long negocioId, ReivindicacaoRequest request) {
        Negocio negocio = negocioRepository.findById(negocioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Negócio não encontrado."));

        // Valida regras de negócio puras
        domainService.validarReivindicacao(negocio);

        // Opcional: se quiser atualizar o dono direto no negócio usando o método da sua entidade:
        // negocio.reivindicar(usuarioAutenticado);
        // negocioRepository.save(negocio);

        // Cria e salva a reivindicação de verdade no banco
        Reivindicacao reivindicacao = new Reivindicacao();
        reivindicacao.setNegocio(negocio);
        reivindicacao.setUsuario(usuarioAutenticado);
        reivindicacao.setJustificativa(request != null ? request.justificativa() : null);
        reivindicacao.setCriadoEm(LocalDateTime.now());

        Reivindicacao salva = reivindicacaoRepository.save(reivindicacao);

        return new ReivindicacaoResponse(
                salva.getId(),
                salva.getNegocio().getId(),
                salva.getUsuario().getId(),
                salva.getJustificativa(),
                salva.getCriadoEm()
        );
    }
}