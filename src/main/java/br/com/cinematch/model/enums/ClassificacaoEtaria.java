package br.com.cinematch.model.enums;

public enum ClassificacaoEtaria {
    LIVRE(0),
    DEZ(10),
    DOZE(12),
    QUATORZE(14),
    DEZESSEIS(16),
    DEZOITO(18);

    private final int idadeMinima;

    ClassificacaoEtaria(int idadeMinima) {
        this.idadeMinima = idadeMinima;
    }

    public int getIdadeMinima() {
        return idadeMinima;
    }

    public boolean permite(ClassificacaoEtaria classificacaoDoFilme) {
        return classificacaoDoFilme.getIdadeMinima() <= this.idadeMinima;
    }
}
