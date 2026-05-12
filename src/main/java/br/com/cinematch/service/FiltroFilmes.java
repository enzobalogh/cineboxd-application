package br.com.cinematch.service;

import br.com.cinematch.model.Filme;
import br.com.cinematch.model.PerfilCinefilo;
import br.com.cinematch.model.enums.Genero;

import java.util.Collections;
import java.util.List;

/**
 * Serviço responsável por remover filmes incompatíveis com o perfil do usuário.
 */
public class FiltroFilmes {

    /**
     * Aplica regras obrigatórias de filtragem sobre o catálogo informado.
     *
     * @param perfil perfil do usuário.
     * @param catalogo lista de filmes disponíveis.
     * @return filmes compatíveis com o perfil.
     */
    public List<Filme> filtrar(PerfilCinefilo perfil, List<Filme> catalogo) {
        if (catalogo == null || catalogo.isEmpty()) {
            return Collections.emptyList();
        }

        return catalogo.stream()
                .filter(filme -> !perfil.jaAssistiu(filme.getId()))
                .filter(filme -> perfil.getClassificacaoMaxima().permite(filme.getClassificacaoEtaria()))
                .filter(filme -> perfil.getIdiomasAceitos().contains(filme.getIdioma()))
                .filter(filme -> naoTemGeneroBloqueado(perfil, filme))
                .toList();
    }

    private boolean naoTemGeneroBloqueado(PerfilCinefilo perfil, Filme filme) {
        for (Genero genero : filme.getGeneros()) {
            if (perfil.getPesosPorGenero().containsKey(genero) && perfil.getPesoGenero(genero) == 0.0) {
                return false;
            }
        }
        return true;
    }
}
