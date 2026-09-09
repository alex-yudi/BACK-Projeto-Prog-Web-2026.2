package br.edu.ufersa.pw.bairro.api.dtos.aviso;

// PUT exige todos os campos deste recurso. So o autor original pode chamar esse endpoint
// (a checagem de quem e o autor acontece no controller/service, nao aqui).
public record AvisoUpdate(CategoriaAviso categoria, String texto) {

    public AvisoUpdate {
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria é obrigatória!");
        }
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("O texto é obrigatório!");
        }
    }
}
