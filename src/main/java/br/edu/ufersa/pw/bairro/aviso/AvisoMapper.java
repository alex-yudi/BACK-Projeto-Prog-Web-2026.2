package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.aviso.dto.AvisoCreate;
import br.edu.ufersa.pw.bairro.aviso.dto.AvisoResponse;
import br.edu.ufersa.pw.bairro.aviso.dto.AvisoUpdate;
import br.edu.ufersa.pw.bairro.negocio.Negocio;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import org.mapstruct.Mapper;

// votosUtil/votosNaoUtil nao vem da entidade (nao ha coluna pra isso): sao contados a parte
// pelo AvaliacaoRepository e passados explicitamente para toResponse.
@Mapper(componentModel = "spring")
public interface AvisoMapper {

    default AvisoResponse toResponse(Aviso entity, long votosUtil, long votosNaoUtil) {
        return new AvisoResponse(
                entity.getId(),
                entity.getNegocio().getId(),
                entity.getAutor().getId(),
                br.edu.ufersa.pw.bairro.aviso.dto.CategoriaAviso.valueOf(entity.getCategoria().name()),
                entity.getTexto(),
                entity.getCriadoEm(),
                votosUtil,
                votosNaoUtil
        );
    }

    // Enum do DTO -> enum de dominio, pelo nome (mesmo padrao do NegocioMapper).
    CategoriaAviso toDomain(br.edu.ufersa.pw.bairro.aviso.dto.CategoriaAviso dto);

    // A entidade nao tem setters: entra pelo construtor validado. O negocio vem por referencia
    // (ja validado pelo NegocioApi no service); o autor e o proprio usuario autenticado.
    default Aviso toEntity(Negocio negocio, Usuario autor, AvisoCreate dto) {
        return new Aviso(negocio, autor, toDomain(dto.categoria()), dto.texto());
    }

    // Altera pelo metodo de dominio, que valida de novo.
    default void atualizarEntidade(AvisoUpdate dto, Aviso entity) {
        entity.atualizar(toDomain(dto.categoria()), dto.texto());
    }
}