package br.com.cinematch.exception;

public class DuracaoInvalidaException extends RuntimeException {
    public DuracaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
