package br.com.cinematch;

import br.com.cinematch.model.Filme;
import br.com.cinematch.service.CatalogoMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("unitario")
class CatalogoMockTest {

    @Test
    @DisplayName("deve possuir trinta filmes no catálogo de exemplo")
    void deve_PossuirTrintaFilmes_Quando_CatalogoMockForCriado() {
        CatalogoMock catalogo = new CatalogoMock();

        List<Filme> filmes = catalogo.buscarTodos();

        assertEquals(30, filmes.size());
    }

    @Test
    @DisplayName("deve conter filmes com pelo menos um gênero")
    void deve_ConterGeneros_Quando_FilmesForemListados() {
        CatalogoMock catalogo = new CatalogoMock();

        List<Filme> filmes = catalogo.buscarTodos();

        assertTrue(filmes.stream().allMatch(filme -> !filme.getGeneros().isEmpty()));
    }
}
