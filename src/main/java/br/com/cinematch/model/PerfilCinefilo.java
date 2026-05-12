package br.com.cinematch.model;

import br.com.cinematch.exception.DuracaoInvalidaException;
import br.com.cinematch.exception.NotaInvalidaException;
import br.com.cinematch.exception.PesoInvalidoException;
import br.com.cinematch.model.enums.ClassificacaoEtaria;
import br.com.cinematch.model.enums.Genero;
import br.com.cinematch.model.enums.Idioma;

import java.util.*;

public class PerfilCinefilo {
    private final Map<Genero, Double> pesosPorGenero = new EnumMap<>(Genero.class);
    private final Set<Idioma> idiomasAceitos = new HashSet<>();
    private final Set<String> historicoAssistidos = new HashSet<>();
    private final Map<String, Integer> notasPorFilme = new HashMap<>();

    private int duracaoMinima;
    private int duracaoMaxima;
    private ClassificacaoEtaria classificacaoMaxima;
    private boolean notificacoesAtivas;

    public PerfilCinefilo(int duracaoMinima, int duracaoMaxima,
                          ClassificacaoEtaria classificacaoMaxima,
                          Collection<Idioma> idiomasAceitos,
                          boolean notificacoesAtivas) {
        definirFaixaDuracao(duracaoMinima, duracaoMaxima);
        this.classificacaoMaxima = Objects.requireNonNull(classificacaoMaxima);
        this.idiomasAceitos.addAll(Objects.requireNonNull(idiomasAceitos));
        this.notificacoesAtivas = notificacoesAtivas;
    }

    public void setPesoGenero(Genero genero, double peso) {
        if (peso < 0.0 || peso > 1.0) {
            throw new PesoInvalidoException("Peso deve estar entre 0.0 e 1.0");
        }
        pesosPorGenero.put(genero, peso);
    }

    public double getPesoGenero(Genero genero) {
        return pesosPorGenero.getOrDefault(genero, 0.0);
    }

    public void definirFaixaDuracao(int duracaoMinima, int duracaoMaxima) {
        if (duracaoMinima > duracaoMaxima) {
            throw new DuracaoInvalidaException("Duração mínima não pode ser maior que a máxima");
        }
        this.duracaoMinima = duracaoMinima;
        this.duracaoMaxima = duracaoMaxima;
    }

    public void adicionarNota(String filmeId, int nota) {
        if (nota < 1 || nota > 5) {
            throw new NotaInvalidaException("Nota deve estar entre 1 e 5");
        }
        notasPorFilme.put(filmeId, nota);
    }

    public Integer getNotaPara(String filmeId) {
        return notasPorFilme.get(filmeId);
    }

    public void marcarComoAssistido(String filmeId) {
        historicoAssistidos.add(filmeId);
    }

    public boolean jaAssistiu(String filmeId) {
        return historicoAssistidos.contains(filmeId);
    }

    public Map<Genero, Double> getPesosPorGenero() {
        return Collections.unmodifiableMap(pesosPorGenero);
    }

    public Set<Idioma> getIdiomasAceitos() {
        return Collections.unmodifiableSet(idiomasAceitos);
    }

    public Set<String> getHistoricoAssistidos() {
        return Collections.unmodifiableSet(historicoAssistidos);
    }

    public Map<String, Integer> getNotasPorFilme() {
        return Collections.unmodifiableMap(notasPorFilme);
    }

    public int getDuracaoMinima() {
        return duracaoMinima;
    }

    public int getDuracaoMaxima() {
        return duracaoMaxima;
    }

    public ClassificacaoEtaria getClassificacaoMaxima() {
        return classificacaoMaxima;
    }

    public boolean isNotificacoesAtivas() {
        return notificacoesAtivas;
    }

    public void setNotificacoesAtivas(boolean notificacoesAtivas) {
        this.notificacoesAtivas = notificacoesAtivas;
    }
}
