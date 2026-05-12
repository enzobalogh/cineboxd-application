package br.com.cinematch.exception;

public class NotaInvalidaException extends RuntimeException {
    public NotaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
