package br.com.cinematch.exception;

public class PerfilIncompletoException extends RuntimeException {
    public PerfilIncompletoException(String mensagem) {
        super(mensagem);
    }
}
