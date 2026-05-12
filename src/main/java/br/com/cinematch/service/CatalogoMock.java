package br.com.cinematch.service;

import br.com.cinematch.model.Filme;
import br.com.cinematch.model.enums.ClassificacaoEtaria;
import br.com.cinematch.model.enums.Genero;
import br.com.cinematch.model.enums.Idioma;

import java.util.List;

public class CatalogoMock implements CatalogoFilmesAPI {
    @Override
    public List<Filme> buscarTodos() {
        return List.of(
                new Filme("F01", "A Chegada", 2016, 116, List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 84),
                new Filme("F02", "Duna: Parte Dois", 2024, 166, List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA), ClassificacaoEtaria.QUATORZE, Idioma.INGLES, 92),
                new Filme("F03", "Ela", 2013, 126, List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA, Genero.ROMANCE), ClassificacaoEtaria.DEZESSEIS, Idioma.INGLES, 78),
                new Filme("F04", "Click", 2006, 107, List.of(Genero.COMEDIA, Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 65),
                new Filme("F05", "O Iluminado", 1980, 146, List.of(Genero.TERROR), ClassificacaoEtaria.DEZOITO, Idioma.INGLES, 88)
        );
    }
}
