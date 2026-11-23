package br.edu.ufersa.pw.bairro.negocio;

import br.edu.ufersa.pw.bairro.negocio.dto.ReivindicacaoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReivindicacaoMapper {

    // decididaPor nulo (ainda PENDENTE) gera decididaPorId nulo. O enum de dominio vira o do DTO pelo nome.
    @Mapping(source = "negocio.id", target = "negocioId")
    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "decididaPor.id", target = "decididaPorId")
    ReivindicacaoResponse toResponse(Reivindicacao entity);

    List<ReivindicacaoResponse> toResponseList(List<Reivindicacao> entities);

    // Enum do DTO -> enum de dominio, pelo nome. Usado no filtro da tela do ADMIN.
    StatusReivindicacao toDomain(br.edu.ufersa.pw.bairro.negocio.dto.StatusReivindicacao dto);
}
