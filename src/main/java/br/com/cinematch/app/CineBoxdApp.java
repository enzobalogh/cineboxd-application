package br.com.cinematch.app;

import br.com.cinematch.exception.DuracaoInvalidaException;
import br.com.cinematch.exception.PesoInvalidoException;
import br.com.cinematch.model.PerfilCinefilo;
import br.com.cinematch.model.Recomendacao;
import br.com.cinematch.model.Usuario;
import br.com.cinematch.model.enums.ClassificacaoEtaria;
import br.com.cinematch.model.enums.Genero;
import br.com.cinematch.model.enums.Humor;
import br.com.cinematch.model.enums.Idioma;
import br.com.cinematch.service.*;
import br.com.cinematch.util.GeradorAleatorioPadrao;

import java.util.*;

public class CineBoxdApp {

    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opcao: ");
            System.out.println();
            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> listarUsuarios();
                case 3 -> recomendarFilmes();
                case 4 -> System.out.println("Encerrando CineMatch. Ate mais!");
                default -> System.out.println("Opcao invalida. Tente novamente.");
            }
            System.out.println();
        } while (opcao != 4);
    }

    private static void exibirMenu() {
        System.out.println("===== CINEMATCH =====");
        System.out.println("1. Cadastrar usuario");
        System.out.println("2. Listar usuarios");
        System.out.println("3. Recomendar filmes");
        System.out.println("4. Sair");
    }

    private static void cadastrarUsuario() {
        System.out.println("--- Cadastro de Usuario ---");

        String nome = lerString("Nome: ");
        int idade = lerInteiro("Idade: ");

        System.out.println("\n--- Perfil Cinefilo ---");

        int duracaoMin = lerInteiro("Duracao minima (minutos): ");
        int duracaoMax = lerInteiro("Duracao maxima (minutos): ");

        ClassificacaoEtaria classificacaoMax = lerClassificacaoEtaria();
        Set<Idioma> idiomas = lerIdiomas();

        String notifResp = lerString("Receber notificacoes? (s/n): ");
        boolean notificacoesAtivas = notifResp.equalsIgnoreCase("s");

        PerfilCinefilo perfil;
        try {
            perfil = new PerfilCinefilo(duracaoMin, duracaoMax, classificacaoMax, idiomas, notificacoesAtivas);
        } catch (DuracaoInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        System.out.println("\n--- Pesos por Genero (0.0 a 1.0) ---");
        for (Genero genero : Genero.values()) {
            double peso = lerDouble("Peso para " + genero + ": ");
            try {
                perfil.setPesoGenero(genero, peso);
            } catch (PesoInvalidoException e) {
                System.out.println("Erro: " + e.getMessage() + ". Definindo como 0.0.");
                perfil.setPesoGenero(genero, 0.0);
            }
        }

        Usuario usuario = new Usuario(nome, idade, perfil);
        usuarios.add(usuario);
        System.out.println("\nUsuario \"" + nome + "\" cadastrado com sucesso!");
    }

    private static void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado.");
            return;
        }
        System.out.println("--- Usuarios Cadastrados ---");
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            PerfilCinefilo p = u.getPerfil();
            System.out.println((i + 1) + ". " + u.getNome() + " (" + u.getIdade() + " anos)");
            System.out.println("   Duração: " + p.getDuracaoMinima() + "-" + p.getDuracaoMaxima() + "min");
            System.out.println("   Classificacao maxima: " + p.getClassificacaoMaxima());
            System.out.println("   Idiomas: " + p.getIdiomasAceitos());
            System.out.println("   Notificacoes: " + (p.isNotificacoesAtivas() ? "Sim" : "Nao"));
            System.out.println("   Pesos: " + p.getPesosPorGenero());
            System.out.println();
        }
    }

    private static void recomendarFilmes() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado. Cadastre um usuario primeiro.");
            return;
        }

        System.out.println("--- Recomendar Filmes ---");
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println((i + 1) + ". " + usuarios.get(i).getNome());
        }
        int escolha = lerInteiro("Escolha um usuario: ");
        if (escolha < 1 || escolha > usuarios.size()) {
            System.out.println("Usuario invalido.");
            return;
        }
        Usuario usuario = usuarios.get(escolha - 1);

        System.out.println();
        Humor humor = lerHumor();
        int topN = lerInteiro("Quantas recomendacoes? ");

        RecomendadorService service = criarRecomendadorService();
        List<Recomendacao> recomendacoes;

        if (humor.isSurpresa()) {
            Recomendacao aleatoria = service.recomendarAleatorio(usuario);
            if (aleatoria == null) {
                System.out.println("Nenhuma recomendacao encontrada para " + usuario.getNome() + ".");
                return;
            }
            recomendacoes = List.of(aleatoria);
        } else {
            recomendacoes = service.recomendar(usuario, topN, humor);
        }

        if (recomendacoes.isEmpty()) {
            System.out.println("Nenhuma recomendacao encontrada para " + usuario.getNome() + ".");
            return;
        }

        System.out.println("\n--- Recomendacoes para " + usuario.getNome() + " ---");
        for (int i = 0; i < recomendacoes.size(); i++) {
            Recomendacao r = recomendacoes.get(i);
            System.out.printf("%d. %s (Score: %.1f)%n", i + 1, r.getFilme().getTitulo(), r.getScore());
            System.out.println("   " + r.getJustificativa());
        }
    }

    private static RecomendadorService criarRecomendadorService() {
        return new RecomendadorService(
                new CatalogoMock(),
                new ConsoleHistoricoRepository(),
                new ConsoleNotificador(),
                new GeradorAleatorioPadrao(),
                new CalculadoraScore(),
                new FiltroFilmes()
        );
    }

    private static ClassificacaoEtaria lerClassificacaoEtaria() {
        System.out.println("Classificacao etaria maxima:");
        ClassificacaoEtaria[] valores = ClassificacaoEtaria.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + ". " + valores[i] + " (" + valores[i].getIdadeMinima() + "+)");
        }
        int escolha = lerInteiro("Escolha: ");
        if (escolha < 1 || escolha > valores.length) {
            System.out.println("Opcao invalida. Usando LIVRE.");
            return ClassificacaoEtaria.LIVRE;
        }
        return valores[escolha - 1];
    }

    private static Set<Idioma> lerIdiomas() {
        Set<Idioma> selecionados = new HashSet<>();
        System.out.println("Idiomas aceitos (digite os numeros separados por espaco):");
        Idioma[] valores = Idioma.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + ". " + valores[i]);
        }
        System.out.print("Escolha: ");
        String linha = scanner.nextLine().trim();
        if (linha.isEmpty()) {
            System.out.println("Nenhum idioma selecionado. Usando INGLES.");
            selecionados.add(Idioma.INGLES);
            return selecionados;
        }
        for (String parte : linha.split("\\s+")) {
            try {
                int idx = Integer.parseInt(parte);
                if (idx >= 1 && idx <= valores.length) {
                    selecionados.add(valores[idx - 1]);
                }
            } catch (NumberFormatException ignored) {
            }
        }
        if (selecionados.isEmpty()) {
            System.out.println("Nenhum idioma valido. Usando INGLES.");
            selecionados.add(Idioma.INGLES);
        }
        return selecionados;
    }

    private static Humor lerHumor() {
        System.out.println("Qual seu humor hoje?");
        Humor[] valores = Humor.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + ". " + valores[i].name());
        }
        int escolha = lerInteiro("Escolha: ");
        if (escolha < 1 || escolha > valores.length) {
            System.out.println("Opcao invalida. Usando FELIZ.");
            return Humor.FELIZ;
        }
        return valores[escolha - 1];
    }

    private static String lerString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int lerInteiro(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Digite um numero inteiro.");
            }
        }
    }

    private static double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Digite um numero.");
            }
        }
    }

    private static class ConsoleHistoricoRepository implements HistoricoUsuarioRepository {
        @Override
        public void registrarRecomendacao(Usuario usuario, List<Recomendacao> recomendacoes) {
            System.out.println("[HISTORICO] Recomendacoes registradas para " + usuario.getNome() + ":");
            for (Recomendacao r : recomendacoes) {
                System.out.println("  - " + r.getFilme().getTitulo() + " (score: " + String.format("%.1f", r.getScore()) + ")");
            }
        }
    }

    private static class ConsoleNotificador implements NotificadorPush {
        @Override
        public void enviar(Usuario usuario, String mensagem) {
            System.out.println("[NOTIFICACAO] " + usuario.getNome() + ": " + mensagem);
        }
    }
}
