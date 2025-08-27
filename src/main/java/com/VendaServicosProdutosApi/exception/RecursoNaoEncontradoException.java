package com.VendaServicosProdutosApi.exception;

import jakarta.persistence.EntityNotFoundException;

public class RecursoNaoEncontradoException extends EntityNotFoundException {
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
