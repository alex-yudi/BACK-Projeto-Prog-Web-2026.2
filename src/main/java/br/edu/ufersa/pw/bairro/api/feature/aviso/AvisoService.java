package br.edu.ufersa.pw.bairro.api.feature.aviso;

import br.edu.ufersa.pw.bairro.api.feature.aviso.dto.AvisoCreate;
import br.edu.ufersa.pw.bairro.api.feature.aviso.dto.AvisoResponse;
import org.springframework.stereotype.Service;

// Centraliza a lógica de negócios da feature, acessado pelo Controller
@Service
class AvisoService {

    private final AvisoRepository avisoRepository;

    AvisoService(AvisoRepository avisoRepository) {
        this.avisoRepository = avisoRepository;
    }

    public AvisoResponse criarAviso(Long negocioId, AvisoCreate request) {
        // Implementação futura
        return null;
    }
}