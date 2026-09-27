package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.aviso.dto.AvisoCreate;
import br.edu.ufersa.pw.bairro.aviso.dto.AvisoResponse;
import br.edu.ufersa.pw.bairro.aviso.dto.AvisoUpdate;
import br.edu.ufersa.pw.bairro.negocio.Negocio;
import br.edu.ufersa.pw.bairro.negocio.NegocioApi;
import br.edu.ufersa.pw.bairro.shared.Paginacao;
import br.edu.ufersa.pw.bairro.shared.exception.EntidadeNaoEncontradaException;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// SUBSTITUI o antigo AvisoService.java (apague aquele arquivo). Segue o mesmo padrao do
// OfertaApplicationService: coordena o caso de uso, gerencia a transacao e chama repository/mapper.
@Service
class AvisoApplicationService {

    private final AvisoRepository avisoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final AvisoMapper mapper;
    private final NegocioApi negocioApi;
    private final EntityManager em;

    AvisoApplicationService(AvisoRepository avisoRepository, AvaliacaoRepository avaliacaoRepository,
                             AvisoMapper mapper, NegocioApi negocioApi, EntityManager em) {
        this.avisoRepository = avisoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.mapper = mapper;
        this.negocioApi = negocioApi;
        this.em = em;
    }

    // Qualquer usuario autenticado pode postar um aviso sobre um negocio existente.
    @Transactional
    AvisoResponse criar(Usuario autenticado, Long negocioId, AvisoCreate dto) {
        negocioApi.buscarPorId(negocioId); // 404 se o negocio nao existe/foi excluido
        Aviso aviso = mapper.toEntity(em.getReference(Negocio.class, negocioId), autenticado, dto);
        Aviso salvo = avisoRepository.save(aviso);
        return mapper.toResponse(salvo, 0, 0); // aviso recem-criado ainda nao tem avaliacoes
    }

    @Transactional(readOnly = true)
    List<AvisoResponse> listar(Long negocioId, Integer page, Integer size) {
        negocioApi.buscarPorId(negocioId);
        return avisoRepository.findByNegocioId(negocioId, Paginacao.de(page, size))
                .getContent()
                .stream()
                .map(this::comContagemDeVotos)
                .toList();
    }

    // Somente o autor original (ou um ADMIN) pode editar o aviso.
    @Transactional
    AvisoResponse atualizar(Usuario autenticado, Long negocioId, Long avisoId, AvisoUpdate dto) {
        Aviso aviso = obter(negocioId, avisoId);
        exigirAutorOuAdmin(autenticado, aviso);
        mapper.atualizarEntidade(dto, aviso);
        return comContagemDeVotos(aviso);
    }

    // Somente o autor original (ou um ADMIN) pode excluir o aviso.
    @Transactional
    void excluir(Usuario autenticado, Long negocioId, Long avisoId) {
        Aviso aviso = obter(negocioId, avisoId);
        exigirAutorOuAdmin(autenticado, aviso);
        avisoRepository.delete(aviso);
    }

    // Filtra pelo negocio do path: aviso de outro negocio tambem e 404.
    private Aviso obter(Long negocioId, Long avisoId) {
        return avisoRepository.findByIdAndNegocioId(avisoId, negocioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Aviso " + avisoId + " não encontrado."));
    }

    private void exigirAutorOuAdmin(Usuario autenticado, Aviso aviso) {
        autenticado.exigirDonoOuAdmin(aviso.getAutor().getId(),
                "Somente o autor do aviso ou um ADMIN pode alterá-lo.");
    }

    // Pacote-privado de proposito: reaproveitado pelo AvisoApiImpl (GET /usuarios/me/avisos),
    // pra nao duplicar a logica de contagem de votos em dois lugares.
    AvisoResponse comContagemDeVotos(Aviso aviso) {
        long util = avaliacaoRepository.countByAvisoIdAndUtilTrue(aviso.getId());
        long naoUtil = avaliacaoRepository.countByAvisoIdAndUtilFalse(aviso.getId());
        return mapper.toResponse(aviso, util, naoUtil);
    }
}
