package br.com.cinematch.model;

import java.util.Objects;

public class Usuario {
    private final String nome;
    private final int idade;
    private final PerfilCinefilo perfil;

    public Usuario(String nome, int idade, PerfilCinefilo perfil) {
        this.nome = Objects.requireNonNull(nome);
        this.idade = idade;
        this.perfil = Objects.requireNonNull(perfil);
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public PerfilCinefilo getPerfil() {
        return perfil;
    }
}
