package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.aviso.dto.AvisoResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

// Contrato do modulo aviso para os demais (usuario). Nao expoe entidade nem repository.
public interface AvisoApi {

    // GET /api/v1/usuarios/me/avisos - avisos ativos do autor, na ordem do Pageable (use Paginacao.de).
    List<AvisoResponse> listarPorAutor(Long autorId, Pageable pageable);
}
