package br.com.cinematch.service;

import br.com.cinematch.model.Filme;

import java.util.List;

/**
 * Interface que representa a fonte externa de filmes disponíveis para recomendação.
 */
public interface CatalogoFilmesAPI {
    /**
     * Busca todos os filmes disponíveis no catálogo.
     *
     * @return lista de filmes disponíveis.
     */
    List<Filme> buscarTodos();
}
