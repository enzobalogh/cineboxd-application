package br.com.cinematch;

import br.com.cinematch.exception.DuracaoInvalidaException;
import br.com.cinematch.exception.NotaInvalidaException;
import br.com.cinematch.exception.PesoInvalidoException;
import br.com.cinematch.model.PerfilCinefilo;
import br.com.cinematch.model.enums.ClassificacaoEtaria;
import br.com.cinematch.model.enums.Genero;
import br.com.cinematch.model.enums.Idioma;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unitario")
class PerfilCinefiloTest {

    @Test
    @DisplayName("deve aceitar peso quando peso está dentro do intervalo")
    void deve_AceitarPeso_Quando_PesoDentroDoIntervalo() {
        PerfilCinefilo perfil = new PerfilCinefilo(90, 150, ClassificacaoEtaria.DEZESSEIS, List.of(Idioma.INGLES), false);

        perfil.setPesoGenero(Genero.DRAMA, 0.8);

        assertEquals(0.8, perfil.getPesoGenero(Genero.DRAMA));
    }

    @Test
    @DisplayName("deve lançar exceção quando peso está fora do intervalo")
    void deve_LancarExcecao_Quando_PesoForaDoIntervalo() {
        PerfilCinefilo perfil = new PerfilCinefilo(90, 150, ClassificacaoEtaria.DEZESSEIS, List.of(Idioma.INGLES), false);

        assertThrows(PesoInvalidoException.class, () -> perfil.setPesoGenero(Genero.ACAO, 1.5));
    }

    @Test
    @DisplayName("deve lançar exceção quando duração mínima é maior que máxima")
    void deve_LancarExcecao_Quando_DuracaoMinimaMaiorQueMaxima() {
        assertThrows(DuracaoInvalidaException.class,
                () -> new PerfilCinefilo(160, 90, ClassificacaoEtaria.DEZESSEIS, List.of(Idioma.INGLES), false));
    }

    @Test
    @DisplayName("deve lançar exceção quando nota está fora do intervalo")
    void deve_LancarExcecao_Quando_NotaForaDoIntervalo() {
        PerfilCinefilo perfil = new PerfilCinefilo(90, 150, ClassificacaoEtaria.DEZESSEIS, List.of(Idioma.INGLES), false);

        assertThrows(NotaInvalidaException.class, () -> perfil.adicionarNota("F01", 6));
    }

    @Test
    @DisplayName("deve marcar filme como assistido no histórico")
    void deve_MarcarFilme_Quando_Assistido() {
        PerfilCinefilo perfil = new PerfilCinefilo(90, 150, ClassificacaoEtaria.DEZESSEIS, List.of(Idioma.INGLES), false);

        perfil.marcarComoAssistido("F01");

        assertTrue(perfil.jaAssistiu("F01"));
    }
}
