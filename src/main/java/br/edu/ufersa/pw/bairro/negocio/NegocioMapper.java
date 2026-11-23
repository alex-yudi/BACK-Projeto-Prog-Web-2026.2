package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.negocio.dto.NegocioCreate;
import br.edu.ufersa.pw.bairro.negocio.dto.NegocioResponse;
import br.edu.ufersa.pw.bairro.negocio.dto.NegocioUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NegocioMapper {

    // O enum de dominio vira o enum do DTO pelo nome. dono nulo (negocio sem dono) gera donoId nulo.
    @Mapping(source = "dono.id", target = "donoId")
    NegocioResponse toResponse(Negocio entity);

    List<NegocioResponse> toResponseList(List<Negocio> entities);

    // Enum do DTO -> enum de dominio, pelo nome.
    CategoriaNegocio toDomain(br.edu.ufersa.pw.bairro.negocio.dto.CategoriaNegocio dto);

    // A entidade nao tem setters: entra pelo construtor validado. O dono nao vem do DTO (o service o define).
    default Negocio toEntity(NegocioCreate dto) {
        return new Negocio(dto.nome(), toDomain(dto.categoria()), dto.cep(), dto.numero(), dto.bairro(), dto.descricao());
    }

    // Altera pelo metodo de dominio, que valida de novo.
    default void atualizarEntidade(NegocioUpdate dto, Negocio entity) {
        entity.atualizar(dto.nome(), toDomain(dto.categoria()), dto.cep(), dto.numero(), dto.bairro(), dto.descricao());
    }
}
