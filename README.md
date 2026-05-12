# CineBoxd — Recomendador de Filmes por Perfil

Slogan: **O filme certo, na noite certa.**

O CineBoxd é um sistema recomendador de filmes baseado no perfil do usuário. Ele considera preferências como gênero, duração, classificação etária, idioma, histórico de filmes assistidos e notas dadas anteriormente.

## O que foi implementado

- Modelos principais: `Usuario`, `PerfilCinefilo`, `Filme`, `Recomendacao`
- Enums: `Genero`, `Idioma`, `ClassificacaoEtaria`
- Exceptions customizadas: `PesoInvalidoException`, `DuracaoInvalidaException`, `NotaInvalidaException`, `PerfilIncompletoException`
- Serviços: `RecomendadorService`, `FiltroFilmes`, `CalculadoraScore`, `CatalogoMock`
- Interfaces mockáveis: `CatalogoFilmesAPI`, `HistoricoUsuarioRepository`, `NotificadorPush`, `GeradorAleatorio`
- Catálogo mockado com 30 filmes.
- Método principal `recomendar(Usuario usuario, int topN)`.
- Modo `recomendarAleatorio(Usuario usuario)`.
- Testes unitários com JUnit 5.
- Testes com Mockito.
- Uso de `ArgumentCaptor`.
- Teste de integração do pipeline principal.
- Javadocs nos serviços e interfaces principais.

## Como rodar

```bash
mvn test
```

Ou pelo Eclipse:

```text
Botão direito no projeto > Run As > JUnit Test
```

## Arquitetura

### model

Contém as classes de domínio: `Usuario`, `PerfilCinefilo`, `Filme` e `Recomendacao`.

### model/enums

Contém valores fixos usados pelo sistema: `Genero`, `Idioma` e `ClassificacaoEtaria`.

### service

Contém regras de negócio e fluxo principal: `RecomendadorService`, `FiltroFilmes`, `CalculadoraScore` e `CatalogoMock`.

Também contém interfaces que representam dependências externas: `CatalogoFilmesAPI`, `HistoricoUsuarioRepository` e `NotificadorPush`.

### exception

Contém exceções específicas do projeto.

### util

Contém utilitários, como `GeradorAleatorio`.

## Fluxo da recomendação

```text
Usuario
  ↓
RecomendadorService
  ↓
CatalogoFilmesAPI
  ↓
FiltroFilmes
  ↓
CalculadoraScore
  ↓
HistoricoUsuarioRepository
  ↓
NotificadorPush
```

## O que foi mockado

- `CatalogoFilmesAPI`: representa API/fonte externa de filmes.
- `HistoricoUsuarioRepository`: representa persistência/banco de dados.
- `NotificadorPush`: representa serviço externo de notificação.
- `GeradorAleatorio`: evita testes instáveis no modo aleatório.

Não foram mockadas classes de lógica pura como `FiltroFilmes` e `CalculadoraScore`, porque isso esconderia erros reais nas regras de negócio.
