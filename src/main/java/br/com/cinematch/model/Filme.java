package br.com.cinematch.model;

import br.com.cinematch.model.enums.ClassificacaoEtaria;
import br.com.cinematch.model.enums.Genero;
import br.com.cinematch.model.enums.Idioma;

import java.util.List;
import java.util.Objects;

public final class Filme {
    private final String id;
    private final String titulo;
    private final int ano;
    private final int duracaoMinutos;
    private final List<Genero> generos;
    private final ClassificacaoEtaria classificacaoEtaria;
    private final Idioma idioma;
    private final int popularidade;

    public Filme(String id, String titulo, int ano, int duracaoMinutos,
                 List<Genero> generos, ClassificacaoEtaria classificacaoEtaria,
                 Idioma idioma, int popularidade) {
        this.id = Objects.requireNonNull(id);
        this.titulo = Objects.requireNonNull(titulo);
        this.ano = ano;
        this.duracaoMinutos = duracaoMinutos;
        this.generos = List.copyOf(Objects.requireNonNull(generos));
        this.classificacaoEtaria = Objects.requireNonNull(classificacaoEtaria);
        this.idioma = Objects.requireNonNull(idioma);
        this.popularidade = popularidade;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAno() {
        return ano;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public ClassificacaoEtaria getClassificacaoEtaria() {
        return classificacaoEtaria;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public int getPopularidade() {
        return popularidade;
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) return true;
        if (!(outro instanceof Filme filme)) return false;
        return id.equals(filme.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
