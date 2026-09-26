package br.edu.ufersa.pw.bairro.oferta;

import br.edu.ufersa.pw.bairro.negocio.Negocio;
import br.edu.ufersa.pw.bairro.oferta.dto.OfertaCreate;
import br.edu.ufersa.pw.bairro.oferta.dto.OfertaResponse;
import br.edu.ufersa.pw.bairro.oferta.dto.OfertaUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OfertaMapper {

    // getId() de um negocio lazy (ou ate excluido) nao dispara consulta.
    @Mapping(source = "negocio.id", target = "negocioId")
    OfertaResponse toResponse(Oferta entity);

    List<OfertaResponse> toResponseList(List<Oferta> entities);

    // A entidade nao tem setters: entra pelo construtor validado. O negocio vem do path (o service o resolve).
    default Oferta toEntity(Negocio negocio, OfertaCreate dto) {
        return new Oferta(negocio, dto.nome(), dto.descricao(), dto.preco(), dto.validade());
    }

    // Altera pelo metodo de dominio, que valida de novo.
    default void atualizarEntidade(OfertaUpdate dto, Oferta entity) {
        entity.atualizar(dto.nome(), dto.descricao(), dto.preco(), dto.validade());
    }
}
