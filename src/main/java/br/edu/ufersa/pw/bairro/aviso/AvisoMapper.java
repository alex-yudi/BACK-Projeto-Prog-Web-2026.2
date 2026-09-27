package br.edu.ufersa.pw.bairro.aviso;

import br.edu.ufersa.pw.bairro.aviso.dto.AvisoCreate;
import br.edu.ufersa.pw.bairro.aviso.dto.AvisoResponse;
import br.edu.ufersa.pw.bairro.aviso.dto.AvisoUpdate;
import br.edu.ufersa.pw.bairro.negocio.Negocio;
import br.edu.ufersa.pw.bairro.usuario.Usuario;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

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

    // A entidade so expoe setters; quem valida categoria/texto nulo/vazio e o construtor
    // compacto do DTO. O negocio vem por referencia (ja validado pelo NegocioApi no service);
    // o autor e o proprio usuario autenticado (mesmo padrao usado em Negocio.reivindicar).
    default Aviso toEntity(Negocio negocio, Usuario autor, AvisoCreate dto) {
        Aviso aviso = new Aviso();
        aviso.setNegocio(negocio);
        aviso.setAutor(autor);
        aviso.setCategoria(toDomain(dto.categoria()));
        aviso.setTexto(dto.texto());
        aviso.setCriadoEm(LocalDateTime.now());
        return aviso;
    }

    default void atualizarEntidade(AvisoUpdate dto, Aviso entity) {
        entity.setCategoria(toDomain(dto.categoria()));
        entity.setTexto(dto.texto());
    }
}