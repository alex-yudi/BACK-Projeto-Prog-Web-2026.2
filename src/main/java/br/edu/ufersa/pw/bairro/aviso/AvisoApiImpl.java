package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.aviso.dto.AvisoResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AvisoApiImpl implements AvisoApi {

    private final AvisoRepository avisoRepository;
    private final AvisoMapper avisoMapper;
    private final AvaliacaoRepository avaliacaoRepository;

    public AvisoApiImpl(
            AvisoRepository avisoRepository,
            AvisoMapper avisoMapper,
            AvaliacaoRepository avaliacaoRepository
    ) {
        this.avisoRepository = avisoRepository;
        this.avisoMapper = avisoMapper;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @Override
    public List<AvisoResponse> buscarPorAutorId(Long autorId, Integer page, Integer size) {
        int pagina = (page != null) ? page : 0;
        int tamanho = (size != null) ? size : 10;
        Pageable pageable = PageRequest.of(pagina, tamanho);

        // Extrai a lista do Page e mapeia automaticamente com o MapStruct
        return avisoRepository.findByAutorId(autorId, pageable)
                .getContent()
                .stream()
                .map(aviso -> avisoMapper.toResponse(
                        aviso,
                        avaliacaoRepository.countByAvisoIdAndUtilTrue(aviso.getId()),
                        avaliacaoRepository.countByAvisoIdAndUtilFalse(aviso.getId())
                ))
                .toList();
    }
}