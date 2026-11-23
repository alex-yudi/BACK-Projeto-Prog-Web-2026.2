package br.edu.ufersa.pw.bairro.aviso.dto;

// boolean primitivo nao pode ser nulo, entao nao ha o que validar no construtor compacto.
public record AvaliacaoRequest(boolean util) {
}
