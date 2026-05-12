package br.com.cinematch;

import br.com.cinematch.model.Filme;
import br.com.cinematch.model.PerfilCinefilo;
import br.com.cinematch.model.Usuario;
import br.com.cinematch.model.enums.ClassificacaoEtaria;
import br.com.cinematch.model.enums.Genero;
import br.com.cinematch.model.enums.Idioma;

import java.util.List;

public class BaseTeste {
    protected PerfilCinefilo perfilMaria() {
        PerfilCinefilo perfil = new PerfilCinefilo(
                90,
                150,
                ClassificacaoEtaria.DEZESSEIS,
                List.of(Idioma.PORTUGUES, Idioma.INGLES),
                false
        );

        perfil.setPesoGenero(Genero.FICCAO_CIENTIFICA, 0.9);
        perfil.setPesoGenero(Genero.DRAMA, 0.6);
        perfil.setPesoGenero(Genero.COMEDIA, 0.5);
        perfil.setPesoGenero(Genero.TERROR, 0.0);
        perfil.setPesoGenero(Genero.ROMANCE, 0.4);
        perfil.setPesoGenero(Genero.ACAO, 0.7);
        perfil.adicionarNota("F99", 5);

        return perfil;
    }

    protected Usuario usuarioMaria() {
        return new Usuario("Maria", 28, perfilMaria());
    }

    protected Filme chegada() {
        return new Filme("F01", "A Chegada", 2016, 116,
                List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA),
                ClassificacaoEtaria.DOZE,
                Idioma.INGLES,
                84);
    }

    protected Filme duna() {
        return new Filme("F02", "Duna: Parte Dois", 2024, 166,
                List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA),
                ClassificacaoEtaria.QUATORZE,
                Idioma.INGLES,
                92);
    }

    protected Filme iluminado() {
        return new Filme("F03", "O Iluminado", 1980, 146,
                List.of(Genero.TERROR),
                ClassificacaoEtaria.DEZOITO,
                Idioma.INGLES,
                88);
    }

    protected Filme filmeFrances() {
        return new Filme("F04", "Filme Francês", 2020, 110,
                List.of(Genero.DRAMA),
                ClassificacaoEtaria.DOZE,
                Idioma.FRANCES,
                60);
    }

    protected Filme click() {
        return new Filme("F05", "Click", 2006, 107,
                List.of(Genero.COMEDIA, Genero.DRAMA),
                ClassificacaoEtaria.DOZE,
                Idioma.INGLES,
                65);
    }
}
