package br.com.cinematch.service;

import br.com.cinematch.model.Recomendacao;
import br.com.cinematch.model.Usuario;

import java.util.List;

/**
 * Interface responsável por registrar recomendações feitas ao usuário.
 */
public interface HistoricoUsuarioRepository {
    /**
     * Registra no histórico a lista de recomendações geradas.
     *
     * @param usuario usuário que recebeu as recomendações.
     * @param recomendacoes recomendações geradas pelo sistema.
     */
    void registrarRecomendacao(Usuario usuario, List<Recomendacao> recomendacoes);
}
