package br.edu.ufersa.pw.bairro.api.dtos.aviso;

// negocioId vem do path (/negocios/{negocioId}/avisos), autor vem do usuario autenticado -
// nenhum dos dois é campo deste DTO.
public record AvisoCreate(CategoriaAviso categoria, String texto) {

    public AvisoCreate {
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria é obrigatória!");
        }
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("O texto é obrigatório!");
        }
    }
}
