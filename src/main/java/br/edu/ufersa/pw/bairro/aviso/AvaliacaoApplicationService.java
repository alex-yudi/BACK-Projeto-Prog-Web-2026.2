package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.aviso.dto.AvaliacaoRequest;
import br.edu.ufersa.pw.bairro.aviso.dto.AvaliacaoResponse;
import br.edu.ufersa.pw.bairro.negocio.NegocioApi;
import br.edu.ufersa.pw.bairro.shared.exception.EntidadeNaoEncontradaException;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
class AvaliacaoApplicationService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AvisoRepository avisoRepository;
    private final NegocioApi negocioApi;

    AvaliacaoApplicationService(AvaliacaoRepository avaliacaoRepository, AvisoRepository avisoRepository,
                                 NegocioApi negocioApi) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.avisoRepository = avisoRepository;
        this.negocioApi = negocioApi;
    }

    // PUT .../votos/me e idempotente: se o usuario ja avaliou esse aviso, so atualiza o voto
    // existente (util <-> nao-util); senao, cria uma avaliacao nova (unique constraint
    // aviso_id+usuario_id garante que nunca existe mais de uma). Qualquer usuario autenticado
    // pode votar - nao ha restricao de autor/dono aqui.
    @Transactional
    AvaliacaoResponse votar(Usuario autenticado, Long negocioId, Long avisoId, AvaliacaoRequest dto) {
        negocioApi.buscarPorId(negocioId); // 404 se o negocio nao existe/foi excluido
        var aviso = avisoRepository.findByIdAndNegocioId(avisoId, negocioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Aviso " + avisoId + " não encontrado."));

        Avaliacao avaliacao = avaliacaoRepository.findByAvisoIdAndUsuarioId(avisoId, autenticado.getId())
                .orElseGet(Avaliacao::new);

        if (avaliacao.getId() == null) {
            avaliacao.setAviso(aviso);
            avaliacao.setUsuario(autenticado);
            avaliacao.setCriadoEm(LocalDateTime.now());
        }
        avaliacao.setUtil(dto.util());

        Avaliacao salva = avaliacaoRepository.save(avaliacao);
        return new AvaliacaoResponse(salva.getId(), avisoId, autenticado.getId(), salva.isUtil(), salva.getCriadoEm());
    }
}
