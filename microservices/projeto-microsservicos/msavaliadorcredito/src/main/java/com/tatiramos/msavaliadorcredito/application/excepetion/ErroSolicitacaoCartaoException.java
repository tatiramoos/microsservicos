package com.tatiramos.msavaliadorcredito.application.excepetion;

public class ErroSolicitacaoCartaoException extends RuntimeException {
    public ErroSolicitacaoCartaoException(String message){
        super(message);
    }
}
