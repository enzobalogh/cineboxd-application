package br.com.cinematch.util;

import java.util.Random;

public class GeradorAleatorioPadrao implements GeradorAleatorio {
    private final Random random = new Random();

    @Override
    public int sortearInteiro(int minimoInclusivo, int maximoExclusivo) {
        return random.nextInt(minimoInclusivo, maximoExclusivo);
    }
}
