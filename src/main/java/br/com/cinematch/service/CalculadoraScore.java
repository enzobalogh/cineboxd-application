package br.com.cinematch.service;

import br.com.cinematch.model.Filme;
import br.com.cinematch.model.PerfilCinefilo;
import br.com.cinematch.model.enums.Genero;

import java.util.Map;

/**
 * Serviço responsável por calcular o score de compatibilidade entre perfil e filme.
 */
public class CalculadoraScore {
    public static final double PESO_GENERO = 0.50;
    public static final double PESO_DURACAO = 0.20;
    public static final double PESO_POPULARIDADE = 0.15;
    public static final double PESO_AFINIDADE = 0.15;

    /**
     * Calcula o score final de compatibilidade do filme.
     *
     * @param perfil perfil usado como base da recomendação.
     * @param filme filme avaliado.
     * @return score entre 0 e 100.
     */
    public double calcular(PerfilCinefilo perfil, Filme filme) {
        double scoreGenero = calcularScoreGenero(perfil, filme);
        double scoreDuracao = calcularScoreDuracao(perfil, filme);
        double scorePopularidade = filme.getPopularidade();
        double scoreAfinidade = calcularAfinidadeHistorica(perfil, filme);

        double total = (scoreGenero * PESO_GENERO)
                + (scoreDuracao * PESO_DURACAO)
                + (scorePopularidade * PESO_POPULARIDADE)
                + (scoreAfinidade * PESO_AFINIDADE);

        return limitarEntreZeroECem(total);
    }

    public double calcular(PerfilCinefilo perfil, Filme filme, Map<Genero, Double> multiplicadores) {
        double scoreGenero = calcularScoreGenero(perfil, filme, multiplicadores);
        double scoreDuracao = calcularScoreDuracao(perfil, filme);
        double scorePopularidade = filme.getPopularidade();
        double scoreAfinidade = calcularAfinidadeHistorica(perfil, filme);

        double total = (scoreGenero * PESO_GENERO)
                + (scoreDuracao * PESO_DURACAO)
                + (scorePopularidade * PESO_POPULARIDADE)
                + (scoreAfinidade * PESO_AFINIDADE);

        return limitarEntreZeroECem(total);
    }

    /**
     * Calcula a compatibilidade de gênero com base nos pesos do perfil.
     *
     * @param perfil perfil usado na comparação.
     * @param filme filme avaliado.
     * @return score de gênero entre 0 e 100.
     */
    public double calcularScoreGenero(PerfilCinefilo perfil, Filme filme) {
        if (filme.getGeneros().isEmpty()) {
            return 0.0;
        }

        double soma = 0.0;
        for (Genero genero : filme.getGeneros()) {
            soma += perfil.getPesoGenero(genero);
        }

        return (soma / filme.getGeneros().size()) * 100.0;
    }

    private double calcularScoreGenero(PerfilCinefilo perfil, Filme filme, Map<Genero, Double> multiplicadores) {
        if (filme.getGeneros().isEmpty()) {
            return 0.0;
        }

        double soma = 0.0;
        for (Genero genero : filme.getGeneros()) {
            double peso = perfil.getPesoGenero(genero);
            double mult = multiplicadores.getOrDefault(genero, 1.0);
            soma += peso * mult;
        }

        return (soma / filme.getGeneros().size()) * 100.0;
    }

    /**
     * Calcula a aderência da duração do filme à faixa preferida.
     *
     * @param perfil perfil com duração mínima e máxima.
     * @param filme filme avaliado.
     * @return score de duração entre 0 e 100.
     */
    public double calcularScoreDuracao(PerfilCinefilo perfil, Filme filme) {
        int duracao = filme.getDuracaoMinutos();

        if (duracao >= perfil.getDuracaoMinima() && duracao <= perfil.getDuracaoMaxima()) {
            return 100.0;
        }

        int distancia;
        if (duracao < perfil.getDuracaoMinima()) {
            distancia = perfil.getDuracaoMinima() - duracao;
        } else {
            distancia = duracao - perfil.getDuracaoMaxima();
        }

        return Math.max(0.0, 100.0 - distancia);
    }

    private double calcularAfinidadeHistorica(PerfilCinefilo perfil, Filme filme) {
        if (perfil.getNotasPorFilme().isEmpty()) {
            return 50.0;
        }

        double mediaNotas = perfil.getNotasPorFilme().values()
                .stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(3.0);

        return (mediaNotas / 5.0) * 100.0;
    }

    private double limitarEntreZeroECem(double valor) {
        return Math.max(0.0, Math.min(100.0, valor));
    }
}
