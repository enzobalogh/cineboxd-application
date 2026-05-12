package br.com.cinematch.service;

import br.com.cinematch.model.Filme;
import br.com.cinematch.model.enums.ClassificacaoEtaria;
import br.com.cinematch.model.enums.Genero;
import br.com.cinematch.model.enums.Idioma;

import java.util.List;

/**
 * Catálogo fixo usado para demonstração do sistema sem depender de API externa.
 */
public class CatalogoMock implements CatalogoFilmesAPI {
    /**
     * Retorna uma lista fixa com 30 filmes para testes manuais e demonstrações.
     *
     * @return lista de filmes disponíveis.
     */
    @Override
    public List<Filme> buscarTodos() {
        return List.of(
                new Filme("F01", "A Chegada", 2016, 116, List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 84),
                new Filme("F02", "Duna: Parte Dois", 2024, 166, List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA, Genero.AVENTURA), ClassificacaoEtaria.QUATORZE, Idioma.INGLES, 92),
                new Filme("F03", "Ela", 2013, 126, List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA, Genero.ROMANCE), ClassificacaoEtaria.DEZESSEIS, Idioma.INGLES, 78),
                new Filme("F04", "Click", 2006, 107, List.of(Genero.COMEDIA, Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 65),
                new Filme("F05", "O Iluminado", 1980, 146, List.of(Genero.TERROR), ClassificacaoEtaria.DEZOITO, Idioma.INGLES, 88),
                new Filme("F06", "Interestelar", 2014, 169, List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 95),
                new Filme("F07", "Matrix", 1999, 136, List.of(Genero.FICCAO_CIENTIFICA, Genero.ACAO), ClassificacaoEtaria.QUATORZE, Idioma.INGLES, 90),
                new Filme("F08", "Blade Runner 2049", 2017, 164, List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA), ClassificacaoEtaria.QUATORZE, Idioma.INGLES, 86),
                new Filme("F09", "Tropa de Elite", 2007, 115, List.of(Genero.ACAO, Genero.DRAMA, Genero.CRIME), ClassificacaoEtaria.DEZOITO, Idioma.PORTUGUES, 80),
                new Filme("F10", "Cidade de Deus", 2002, 130, List.of(Genero.DRAMA, Genero.CRIME), ClassificacaoEtaria.DEZOITO, Idioma.PORTUGUES, 89),
                new Filme("F11", "O Auto da Compadecida", 2000, 104, List.of(Genero.COMEDIA, Genero.DRAMA), ClassificacaoEtaria.DEZ, Idioma.PORTUGUES, 87),
                new Filme("F12", "Central do Brasil", 1998, 110, List.of(Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.PORTUGUES, 82),
                new Filme("F13", "O Senhor dos Anéis", 2001, 178, List.of(Genero.FANTASIA, Genero.AVENTURA, Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 96),
                new Filme("F14", "Harry Potter e a Pedra Filosofal", 2001, 152, List.of(Genero.FANTASIA, Genero.AVENTURA), ClassificacaoEtaria.LIVRE, Idioma.INGLES, 85),
                new Filme("F15", "Toy Story", 1995, 81, List.of(Genero.ANIMACAO, Genero.COMEDIA, Genero.AVENTURA), ClassificacaoEtaria.LIVRE, Idioma.INGLES, 88),
                new Filme("F16", "Procurando Nemo", 2003, 100, List.of(Genero.ANIMACAO, Genero.AVENTURA, Genero.COMEDIA), ClassificacaoEtaria.LIVRE, Idioma.INGLES, 86),
                new Filme("F17", "Divertida Mente", 2015, 95, List.of(Genero.ANIMACAO, Genero.COMEDIA, Genero.DRAMA), ClassificacaoEtaria.LIVRE, Idioma.INGLES, 83),
                new Filme("F18", "Parasita", 2019, 132, List.of(Genero.DRAMA, Genero.SUSPENSE), ClassificacaoEtaria.DEZESSEIS, Idioma.ESPANHOL, 90),
                new Filme("F19", "O Labirinto do Fauno", 2006, 118, List.of(Genero.FANTASIA, Genero.DRAMA), ClassificacaoEtaria.DEZESSEIS, Idioma.ESPANHOL, 81),
                new Filme("F20", "Amélie Poulain", 2001, 122, List.of(Genero.ROMANCE, Genero.COMEDIA), ClassificacaoEtaria.QUATORZE, Idioma.FRANCES, 79),
                new Filme("F21", "A Viagem de Chihiro", 2001, 125, List.of(Genero.ANIMACAO, Genero.FANTASIA, Genero.AVENTURA), ClassificacaoEtaria.LIVRE, Idioma.JAPONES, 91),
                new Filme("F22", "Your Name", 2016, 106, List.of(Genero.ANIMACAO, Genero.ROMANCE, Genero.DRAMA), ClassificacaoEtaria.DEZ, Idioma.JAPONES, 85),
                new Filme("F23", "Se7en", 1995, 127, List.of(Genero.SUSPENSE, Genero.CRIME, Genero.DRAMA), ClassificacaoEtaria.DEZOITO, Idioma.INGLES, 88),
                new Filme("F24", "Clube da Luta", 1999, 139, List.of(Genero.DRAMA), ClassificacaoEtaria.DEZOITO, Idioma.INGLES, 89),
                new Filme("F25", "O Grande Hotel Budapeste", 2014, 99, List.of(Genero.COMEDIA, Genero.DRAMA), ClassificacaoEtaria.QUATORZE, Idioma.INGLES, 76),
                new Filme("F26", "La La Land", 2016, 128, List.of(Genero.ROMANCE, Genero.DRAMA, Genero.COMEDIA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 77),
                new Filme("F27", "Mad Max: Estrada da Fúria", 2015, 120, List.of(Genero.ACAO, Genero.AVENTURA, Genero.FICCAO_CIENTIFICA), ClassificacaoEtaria.DEZESSEIS, Idioma.INGLES, 86),
                new Filme("F28", "John Wick", 2014, 101, List.of(Genero.ACAO, Genero.SUSPENSE), ClassificacaoEtaria.DEZESSEIS, Idioma.INGLES, 75),
                new Filme("F29", "O Jogo da Imitação", 2014, 114, List.of(Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 80),
                new Filme("F30", "A Rede Social", 2010, 120, List.of(Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.INGLES, 82)
        );
    }
}
