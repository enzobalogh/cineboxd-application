package br.com.cinematch;

import br.com.cinematch.model.Recomendacao;
import br.com.cinematch.model.Usuario;
import br.com.cinematch.service.*;
import br.com.cinematch.util.GeradorAleatorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("integracao")
class RecomendacaoIntegracaoTest extends BaseTeste {

    @Test
    @DisplayName("deve executar pipeline completo com catálogo mock realista")
    void deve_ExecutarPipelineCompleto_Quando_CatalogoMockRealista() {
        CatalogoFilmesAPI catalogo = new CatalogoMock();
        HistoricoUsuarioRepository historico = (usuario, recomendacoes) -> { };
        NotificadorPush notificador = (usuario, mensagem) -> { };
        GeradorAleatorio gerador = (min, max) -> min;

        RecomendadorService service = new RecomendadorService(
                catalogo,
                historico,
                notificador,
                gerador,
                new CalculadoraScore(),
                new FiltroFilmes()
        );

        Usuario usuario = usuarioMaria();

        List<Recomendacao> resultado = service.recomendar(usuario, 5);

        assertAll(
                () -> assertFalse(resultado.isEmpty()),
                () -> assertTrue(resultado.size() <= 5),
                () -> assertTrue(resultado.get(0).getScore() >= resultado.get(resultado.size() - 1).getScore())
        );
    }
}
