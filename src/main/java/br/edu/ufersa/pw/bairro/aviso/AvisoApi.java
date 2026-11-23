package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.aviso.dto.AvisoResponse;
import java.util.List;

// Interface pública: Porta de entrada para outras features buscarem dados de avisos
public interface AvisoApi {
    List<AvisoResponse> buscarPorAutorId(Long autorId, Integer page, Integer size);
}