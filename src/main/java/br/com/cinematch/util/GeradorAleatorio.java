package br.com.cinematch.util;

/**
 * Interface usada para controlar sorteios e permitir testes previsíveis.
 */
public interface GeradorAleatorio {
    /**
     * Sorteia um número inteiro dentro do intervalo informado.
     *
     * @param minimoInclusivo menor valor permitido, incluso.
     * @param maximoExclusivo maior valor permitido, exclusivo.
     * @return número sorteado.
     */
    int sortearInteiro(int minimoInclusivo, int maximoExclusivo);
}
