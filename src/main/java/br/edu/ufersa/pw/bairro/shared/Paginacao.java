package br.edu.ufersa.pw.bairro.shared;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class Paginacao {

    private static final int TAMANHO_PADRAO = 20;
    private static final int TAMANHO_MAXIMO = 50;

    private Paginacao() {
    }

    // Converte os parametros opcionais page/size da query string em Pageable. Valores fora do limite
    // sao ajustados em vez de rejeitados. A ordem fixa (mais recentes primeiro, por id) evita repetir
    // ou pular itens entre paginas. Nao aceita "sort" do cliente, que poderia ordenar por qualquer campo.
    public static Pageable de(Integer page, Integer size) {
        int pagina = (page == null || page < 0) ? 0 : page;
        int tamanho = (size == null || size < 1) ? TAMANHO_PADRAO : Math.min(size, TAMANHO_MAXIMO);
        return PageRequest.of(pagina, tamanho, Sort.by(Sort.Direction.DESC, "id"));
    }
}
