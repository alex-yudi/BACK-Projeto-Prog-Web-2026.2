package br.edu.ufersa.pw.bairro.negocio;

import org.springframework.stereotype.Service;

@Service
class NegocioDomainService {

    public void validarReivindicacao(Negocio negocio) {
        if (negocio.getDono() != null) {
            // Lança exceção de negócio para o RestControllerAdvice capturar e retornar 409 Conflict
            throw new IllegalStateException("Este negócio já possui um proprietário vinculado.");
        }
    }
}