package br.com.cinematch;

import br.com.cinematch.model.Filme;
import br.com.cinematch.model.PerfilCinefilo;
import br.com.cinematch.model.Recomendacao;
import br.com.cinematch.model.Usuario;
import br.com.cinematch.model.enums.ClassificacaoEtaria;
import br.com.cinematch.model.enums.Genero;
import br.com.cinematch.model.enums.Humor;
import br.com.cinematch.model.enums.Idioma;
import br.com.cinematch.service.*;
import br.com.cinematch.util.GeradorAleatorio;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Tag("unitario")
@ExtendWith(MockitoExtension.class)
class RecomendadorServiceTest extends BaseTeste {
    @Mock private CatalogoFilmesAPI catalogo;
    @Mock private HistoricoUsuarioRepository historico;
    @Mock private NotificadorPush notificador;
    @Mock private GeradorAleatorio gerador;

    private RecomendadorService service;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        service = new RecomendadorService(
                catalogo,
                historico,
                notificador,
                gerador,
                new CalculadoraScore(),
                new FiltroFilmes()
        );

        usuario = usuarioMaria();
    }

    @Test
    @DisplayName("deve retornar recomendações quando catálogo possui filmes válidos")
    void deve_RetornarRecomendacoes_Quando_CatalogoPossuiFilmesValidos() {
        when(catalogo.buscarTodos()).thenReturn(List.of(chegada()));

        List<Recomendacao> resultado = service.recomendar(usuario, 1);

        assertEquals(1, resultado.size());
        verify(catalogo).buscarTodos();
    }

    @Test
    @DisplayName("deve respeitar topN quando recomendar")
    void deve_RespeitarTopN_Quando_Recomendar() {
        when(catalogo.buscarTodos()).thenReturn(List.of(chegada(), filmeFrances(), iluminado()));

        List<Recomendacao> resultado = service.recomendar(usuario, 1);

        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("deve registrar recomendação no histórico após recomendar")
    void deve_RegistrarNoHistorico_Quando_RecomendacaoForGerada() {
        when(catalogo.buscarTodos()).thenReturn(List.of(chegada()));

        List<Recomendacao> resultado = service.recomendar(usuario, 1);

        assertFalse(resultado.isEmpty());
        verify(historico).registrarRecomendacao(eq(usuario), anyList());
    }

    @Test
    @DisplayName("deve retornar lista vazia quando catálogo está vazio")
    void deve_RetornarListaVazia_Quando_CatalogoEstaVazio() {
        when(catalogo.buscarTodos()).thenReturn(List.of());

        List<Recomendacao> resultado = service.recomendar(usuario, 5);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(historico, never()).registrarRecomendacao(any(), anyList());
    }

    @Test
    @DisplayName("deve retornar lista vazia quando catálogo lançar exceção")
    void deve_RetornarListaVazia_Quando_CatalogoLancaExcecao() {
        when(catalogo.buscarTodos()).thenThrow(new RuntimeException("API offline"));

        List<Recomendacao> resultado = service.recomendar(usuario, 5);

        assertTrue(resultado.isEmpty());
        verify(notificador, never()).enviar(any(), anyString());
    }

    @Test
    @DisplayName("deve chamar notificador quando notificações estão ativas")
    void deve_ChamarNotificador_Quando_NotificacoesAtivas() {
        usuario.getPerfil().setNotificacoesAtivas(true);
        when(catalogo.buscarTodos()).thenReturn(List.of(chegada()));

        service.recomendar(usuario, 1);

        verify(notificador).enviar(eq(usuario), anyString());
    }

    @Test
    @DisplayName("deve não chamar notificador quando notificações estão desligadas")
    void deve_NaoChamarNotificador_Quando_NotificacoesDesligadas() {
        usuario.getPerfil().setNotificacoesAtivas(false);
        when(catalogo.buscarTodos()).thenReturn(List.of(chegada()));

        service.recomendar(usuario, 1);

        verify(notificador, never()).enviar(any(), anyString());
    }

    @Test
    @DisplayName("deve capturar recomendações enviadas ao histórico")
    void deve_CapturarRecomendacoes_Quando_RegistrarNoHistorico() {
        when(catalogo.buscarTodos()).thenReturn(List.of(chegada()));

        service.recomendar(usuario, 1);

        ArgumentCaptor<List<Recomendacao>> captor = ArgumentCaptor.forClass(List.class);
        verify(historico).registrarRecomendacao(eq(usuario), captor.capture());

        List<Recomendacao> recomendacoesRegistradas = captor.getValue();

        assertAll(
                () -> assertEquals(1, recomendacoesRegistradas.size()),
                () -> assertEquals("A Chegada", recomendacoesRegistradas.get(0).getFilme().getTitulo()),
                () -> assertNotNull(recomendacoesRegistradas.get(0).getJustificativa())
        );
    }

    @Test
    @DisplayName("deve recomendar filme correto de acordo com o humor")
    void deve_RecomendarFilmeCorreto_DeAcordoComOHumor() {
        PerfilCinefilo perfil = new PerfilCinefilo(90, 180, ClassificacaoEtaria.DEZOITO,
                List.of(Idioma.INGLES), false);
        perfil.setPesoGenero(Genero.FICCAO_CIENTIFICA, 0.6);
        perfil.setPesoGenero(Genero.DRAMA, 0.6);
        perfil.setPesoGenero(Genero.TERROR, 0.4);
        usuario = new Usuario("Pedro", 25, perfil);

        when(catalogo.buscarTodos()).thenReturn(List.of(iluminado(), chegada()));

        List<Recomendacao> resultado = service.recomendar(usuario, 2, Humor.TENSO);

        assertAll("recomendacao por humor tenso",
                () -> assertEquals(2, resultado.size()),
                () -> assertEquals("O Iluminado", resultado.get(0).getFilme().getTitulo(),
                        "Humor TENSO deve impulsionar filmes de terror"),
                () -> assertTrue(resultado.get(0).getScore() > resultado.get(1).getScore(),
                        "Filme de terror deve ter score maior com humor TENSO"),
                () -> assertTrue(resultado.get(0).getJustificativa().contains("TENSO"),
                        "Justificativa deve mencionar o humor")
        );
    }

    @Test
    @DisplayName("deve retornar recomendação aleatória quando modo surpreenda-me for chamado")
    void deve_RetornarRecomendacaoAleatoria_Quando_SurpreendaMeForChamado() {
        when(catalogo.buscarTodos()).thenReturn(List.of(chegada()));
        when(gerador.sortearInteiro(0, 1)).thenReturn(0);

        Recomendacao resultado = service.recomendarAleatorio(usuario);

        assertNotNull(resultado);
        assertEquals("A Chegada", resultado.getFilme().getTitulo());
    }
}
