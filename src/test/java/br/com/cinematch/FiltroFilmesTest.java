package br.com.cinematch;

import br.com.cinematch.model.Filme;
import br.com.cinematch.model.PerfilCinefilo;
import br.com.cinematch.service.FiltroFilmes;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unitario")
class FiltroFilmesTest extends BaseTeste {
    private PerfilCinefilo perfil;
    private FiltroFilmes filtro;

    @BeforeEach
    void setUp() {
        perfil = perfilMaria();
        filtro = new FiltroFilmes();
    }

    @Test
    @DisplayName("deve remover filme quando já foi assistido")
    void deve_RemoverFilme_Quando_JaFoiAssistido() {
        Filme filme = chegada();
        perfil.marcarComoAssistido(filme.getId());

        List<Filme> resultado = filtro.filtrar(perfil, List.of(filme));

        assertFalse(resultado.contains(filme));
    }

    @Test
    @DisplayName("deve remover filme quando classificação está acima da máxima")
    void deve_RemoverFilme_Quando_ClassificacaoAcimaDaMaxima() {
        Filme filme = iluminado();

        List<Filme> resultado = filtro.filtrar(perfil, List.of(filme));

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("deve remover filme quando idioma não é aceito")
    void deve_RemoverFilme_Quando_IdiomaNaoAceito() {
        Filme filme = filmeFrances();

        List<Filme> resultado = filtro.filtrar(perfil, List.of(filme));

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("deve remover filme quando possui gênero com peso zero")
    void deve_RemoverFilme_Quando_GeneroTemPesoZero() {
        Filme filme = iluminado();

        List<Filme> resultado = filtro.filtrar(perfil, List.of(filme));

        assertFalse(resultado.contains(filme));
    }

    @Test
    @DisplayName("deve retornar lista vazia quando catálogo está vazio")
    void deve_RetornarListaVazia_Quando_CatalogoEstaVazio() {
        List<Filme> resultado = filtro.filtrar(perfil, List.of());

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}
