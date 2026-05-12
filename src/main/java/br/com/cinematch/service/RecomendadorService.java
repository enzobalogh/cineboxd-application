package br.com.cinematch.service;

import br.com.cinematch.model.Filme;
import br.com.cinematch.model.Recomendacao;
import br.com.cinematch.model.Usuario;
import br.com.cinematch.model.enums.Genero;
import br.com.cinematch.model.enums.Humor;
import br.com.cinematch.util.GeradorAleatorio;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Serviço principal responsável por orquestrar o fluxo de recomendação de filmes.
 */
public class RecomendadorService {
    private final CatalogoFilmesAPI catalogo;
    private final HistoricoUsuarioRepository historicoRepository;
    private final NotificadorPush notificadorPush;
    private final GeradorAleatorio geradorAleatorio;
    private final CalculadoraScore calculadoraScore;
    private final FiltroFilmes filtroFilmes;

    /**
     * Cria o serviço de recomendação com as dependências necessárias.
     *
     * @param catalogo fonte dos filmes disponíveis.
     * @param historicoRepository repositório usado para registrar recomendações.
     * @param notificadorPush serviço de notificação do usuário.
     * @param geradorAleatorio gerador usado no modo aleatório.
     * @param calculadoraScore calculadora da compatibilidade entre perfil e filme.
     * @param filtroFilmes filtro de regras obrigatórias.
     */
    public RecomendadorService(CatalogoFilmesAPI catalogo,
                               HistoricoUsuarioRepository historicoRepository,
                               NotificadorPush notificadorPush,
                               GeradorAleatorio geradorAleatorio,
                               CalculadoraScore calculadoraScore,
                               FiltroFilmes filtroFilmes) {
        this.catalogo = catalogo;
        this.historicoRepository = historicoRepository;
        this.notificadorPush = notificadorPush;
        this.geradorAleatorio = geradorAleatorio;
        this.calculadoraScore = calculadoraScore;
        this.filtroFilmes = filtroFilmes;
    }

    /**
     * Recomenda filmes para o usuário, respeitando filtros, score e limite topN.
     *
     * @param usuario usuário que receberá as recomendações.
     * @param topN quantidade máxima de recomendações retornadas.
     * @return lista ordenada de recomendações.
     */
    public List<Recomendacao> recomendar(Usuario usuario, int topN) {
        try {
            List<Filme> filmes = catalogo.buscarTodos();
            List<Filme> filmesFiltrados = filtroFilmes.filtrar(usuario.getPerfil(), filmes);

            if (filmesFiltrados.isEmpty()) {
                return Collections.emptyList();
            }

            List<Recomendacao> recomendacoes = filmesFiltrados.stream()
                    .map(filme -> criarRecomendacao(usuario, filme))
                    .sorted(Comparator
                            .comparingDouble(Recomendacao::getScore).reversed()
                            .thenComparing(r -> r.getFilme().getPopularidade(), Comparator.reverseOrder()))
                    .limit(topN)
                    .toList();

            historicoRepository.registrarRecomendacao(usuario, recomendacoes);

            if (usuario.getPerfil().isNotificacoesAtivas() && !recomendacoes.isEmpty()) {
                notificadorPush.enviar(usuario, "Suas recomendações de hoje estão prontas!");
            }

            return recomendacoes;
        } catch (Exception erro) {
            return Collections.emptyList();
        }
    }

    /**
     * Recomenda filmes ajustados pelo humor do dia, aplicando multiplicadores
     * de gênero sobre o score calculado.
     *
     * @param usuario usuário que receberá as recomendações.
     * @param topN   quantidade máxima de recomendações.
     * @param humor  humor do dia que influencia os pesos dos gêneros.
     * @return lista ordenada de recomendações.
     */
    public List<Recomendacao> recomendar(Usuario usuario, int topN, Humor humor) {
        Map<Genero, Double> multiplicadores = humor.getMultiplicadores();
        try {
            List<Filme> filmes = catalogo.buscarTodos();
            List<Filme> filmesFiltrados = filtroFilmes.filtrar(usuario.getPerfil(), filmes);

            if (filmesFiltrados.isEmpty()) {
                return Collections.emptyList();
            }

            List<Recomendacao> recomendacoes = filmesFiltrados.stream()
                    .map(filme -> {
                        double score = calculadoraScore.calcular(usuario.getPerfil(), filme, multiplicadores);
                        String justificativa = "Com base no seu humor \"" + humor.name() + "\", recomendamos "
                                + filme.getTitulo()
                                + " porque combina com seu perfil de generos, duracao e idioma.";
                        return new Recomendacao(filme, score, justificativa);
                    })
                    .sorted(Comparator
                            .comparingDouble(Recomendacao::getScore).reversed()
                            .thenComparing(r -> r.getFilme().getPopularidade(), Comparator.reverseOrder()))
                    .limit(topN)
                    .toList();

            historicoRepository.registrarRecomendacao(usuario, recomendacoes);

            if (usuario.getPerfil().isNotificacoesAtivas() && !recomendacoes.isEmpty()) {
                notificadorPush.enviar(usuario, "Suas recomendacoes de hoje estao prontas!");
            }

            return recomendacoes;
        } catch (Exception erro) {
            return Collections.emptyList();
        }
    }

    /**
     * Retorna uma recomendação aleatória entre os filmes que passaram no filtro.
     *
     * @param usuario usuário que receberá a recomendação.
     * @return uma recomendação aleatória ou null caso nenhuma recomendação exista.
     */
    public Recomendacao recomendarAleatorio(Usuario usuario) {
        List<Recomendacao> recomendacoes = recomendar(usuario, Integer.MAX_VALUE);

        if (recomendacoes.isEmpty()) {
            return null;
        }

        int indice = geradorAleatorio.sortearInteiro(0, recomendacoes.size());
        return recomendacoes.get(indice);
    }

    private Recomendacao criarRecomendacao(Usuario usuario, Filme filme) {
        double score = calculadoraScore.calcular(usuario.getPerfil(), filme);
        String justificativa = "Recomendamos " + filme.getTitulo()
                + " porque combina com seu perfil de gêneros, duração e idioma.";

        return new Recomendacao(filme, score, justificativa);
    }
}
