package br.com.cinematch;

import br.com.cinematch.model.Filme;
import br.com.cinematch.model.enums.ClassificacaoEtaria;
import br.com.cinematch.model.enums.Genero;
import br.com.cinematch.model.enums.Idioma;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unitario")
class FilmeTest {

    @Test
    @DisplayName("deve criar filme com todos os atributos preenchidos")
    void deve_CriarFilme_Quando_AtributosValidos() {
        Filme filme = new Filme("F01", "A Chegada", 2016, 116,
                List.of(Genero.FICCAO_CIENTIFICA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 84);

        assertAll(
                () -> assertEquals("F01", filme.getId()),
                () -> assertEquals("A Chegada", filme.getTitulo()),
                () -> assertEquals(2016, filme.getAno()),
                () -> assertEquals(116, filme.getDuracaoMinutos()),
                () -> assertNotNull(filme.getGeneros())
        );
    }

    @Test
    @DisplayName("deve considerar filmes iguais quando possuem mesmo ID")
    void deve_ConsiderarFilmesIguais_Quando_MesmoId() {
        Filme filme1 = new Filme("F01", "A Chegada", 2016, 116,
                List.of(Genero.FICCAO_CIENTIFICA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 84);

        Filme filme2 = new Filme("F01", "Outro Título", 2020, 100,
                List.of(Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 50);

        assertEquals(filme1, filme2);
    }
}
