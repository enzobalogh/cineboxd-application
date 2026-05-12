package br.com.cinematch;

import br.com.cinematch.model.Filme;
import br.com.cinematch.model.PerfilCinefilo;
import br.com.cinematch.model.enums.ClassificacaoEtaria;
import br.com.cinematch.model.enums.Genero;
import br.com.cinematch.model.enums.Idioma;
import br.com.cinematch.service.CalculadoraScore;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unitario")
class CalculadoraScoreTest extends BaseTeste {
    private PerfilCinefilo perfil;
    private CalculadoraScore calculadora;

    @BeforeEach
    void setUp() {
        perfil = perfilMaria();
        calculadora = new CalculadoraScore();
    }

    @Test
    @DisplayName("deve gerar score alto quando filme possui gênero preferido")
    void deve_GerarScoreAlto_Quando_FilmePossuiGeneroPreferido() {
        double score = calculadora.calcular(perfil, chegada());

        assertTrue(score > 70);
    }

    @Test
    @DisplayName("deve gerar score de duração cem quando filme está dentro da faixa")
    void deve_GerarScoreDuracaoCem_Quando_DentroDaFaixa() {
        double scoreDuracao = calculadora.calcularScoreDuracao(perfil, chegada());

        assertEquals(100.0, scoreDuracao);
    }

    @Test
    @DisplayName("deve reduzir score de duração quando filme está acima da faixa")
    void deve_ReduzirScoreDuracao_Quando_AcimaDaFaixa() {
        Filme filmeLongo = new Filme("F10", "Filme Longo", 2024, 180,
                List.of(Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 70);

        double scoreDuracao = calculadora.calcularScoreDuracao(perfil, filmeLongo);

        assertTrue(scoreDuracao < 100.0);
    }

    @Test
    @DisplayName("deve manter score entre zero e cem")
    void deve_ManterScoreEntreZeroECem_Quando_Calcular() {
        double score = calculadora.calcular(perfil, chegada());

        assertTrue(score >= 0.0 && score <= 100.0);
    }

    @ParameterizedTest
    @CsvSource({
            "1.0, 100.0",
            "0.5, 50.0",
            "0.0, 0.0"
    })
    @DisplayName("deve calcular score de gênero conforme peso informado")
    void deve_CalcularScoreGenero_Quando_PesoInformado(double peso, double esperado) {
        PerfilCinefilo perfilLocal = perfilMaria();
        perfilLocal.setPesoGenero(Genero.ACAO, peso);

        Filme filme = new Filme("F20", "Filme de Ação", 2024, 120,
                List.of(Genero.ACAO), ClassificacaoEtaria.DOZE, Idioma.INGLES, 70);

        double resultado = calculadora.calcularScoreGenero(perfilLocal, filme);

        assertEquals(esperado, resultado);
    }
}
