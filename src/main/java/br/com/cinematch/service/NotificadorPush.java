package br.com.cinematch.service;

import br.com.cinematch.model.Usuario;

/**
 * Interface responsável pelo envio de notificações ao usuário.
 */
public interface NotificadorPush {
    /**
     * Envia uma mensagem de notificação para o usuário.
     *
     * @param usuario usuário que receberá a notificação.
     * @param mensagem conteúdo da mensagem enviada.
     */
    void enviar(Usuario usuario, String mensagem);
}
