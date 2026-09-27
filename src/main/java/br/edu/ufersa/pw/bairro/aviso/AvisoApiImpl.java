package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.aviso.dto.AvisoResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Package-private: os outros modulos so enxergam a interface AvisoApi (mesmo padrao do
// NegocioApiImpl). Usado por UsuarioController na tela "Meus avisos".
@Service
class AvisoApiImpl implements AvisoApi {

    private final AvisoRepository avisoRepository;
    private final AvisoApplicationService applicationService;

    AvisoApiImpl(AvisoRepository avisoRepository, AvisoApplicationService applicationService) {
        this.avisoRepository = avisoRepository;
        this.applicationService = applicationService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AvisoResponse> listarPorAutor(Long autorId, Pageable pageable) {
        return avisoRepository.findByAutorId(autorId, pageable)
                .getContent()
                .stream()
                .map(applicationService::comContagemDeVotos)
                .toList();
    }
}
