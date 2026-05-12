package br.com.cinematch.model.enums;

import java.util.Collections;
import java.util.Map;

public enum Humor {
    FELIZ(Map.of(Genero.COMEDIA, 1.5, Genero.ROMANCE, 1.3, Genero.ACAO, 1.2)),
    TRISTE(Map.of(Genero.DRAMA, 1.5, Genero.ROMANCE, 1.2)),
    TENSO(Map.of(Genero.ACAO, 1.5, Genero.TERROR, 1.5)),
    ROMANTICO(Map.of(Genero.ROMANCE, 1.8, Genero.DRAMA, 1.2)),
    AVENTURA(Map.of(Genero.ACAO, 1.5, Genero.FICCAO_CIENTIFICA, 1.3)),
    REFLEXIVO(Map.of(Genero.DOCUMENTARIO, 1.8, Genero.DRAMA, 1.3)),
    SURPRESA(Collections.emptyMap());

    private final Map<Genero, Double> multiplicadores;

    Humor(Map<Genero, Double> multiplicadores) {
        this.multiplicadores = multiplicadores;
    }

    public Map<Genero, Double> getMultiplicadores() {
        return multiplicadores;
    }

    public boolean isSurpresa() {
        return this == SURPRESA;
    }
}
