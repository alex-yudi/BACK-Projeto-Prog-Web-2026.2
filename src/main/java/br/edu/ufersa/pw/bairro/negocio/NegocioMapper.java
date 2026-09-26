package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.negocio.dto.NegocioResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NegocioMapper {

    // O enum de dominio vira o enum do DTO pelo nome. dono nulo (negocio sem dono) gera donoId nulo.
    @Mapping(source = "dono.id", target = "donoId")
    NegocioResponse toResponse(Negocio entity);
}
