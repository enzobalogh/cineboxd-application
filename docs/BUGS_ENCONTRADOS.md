# Bugs encontrados

## Bug 1 — Peso inválido era aceito

**Descrição:**  
O sistema poderia aceitar peso maior que 1.0 para um gênero.

**Teste que revelou:**  
`deve_LancarExcecao_Quando_PesoForaDoIntervalo`

**Correção aplicada:**  
Foi adicionada validação no método `setPesoGenero`, lançando `PesoInvalidoException` quando o peso é menor que 0.0 ou maior que 1.0.

---

## Bug 2 — Filme já assistido continuava sendo recomendado

**Descrição:**  
O sistema poderia manter na lista filmes que já estavam no histórico do usuário.

**Teste que revelou:**  
`deve_RemoverFilme_Quando_JaFoiAssistido`

**Correção aplicada:**  
O `FiltroFilmes` passou a verificar `perfil.jaAssistiu(filme.getId())` antes de aceitar o filme.

---

## Bug 3 — Catálogo vazio poderia causar retorno incorreto

**Descrição:**  
Quando o catálogo estava vazio, o sistema poderia retornar null ou tentar continuar o fluxo sem necessidade.

**Teste que revelou:**  
`deve_RetornarListaVazia_Quando_CatalogoEstaVazio`

**Correção aplicada:**  
O sistema passou a retornar `Collections.emptyList()` quando não há filmes disponíveis.

---

## Bug 4 — Falha da API derrubava a recomendação

**Descrição:**  
Se o catálogo lançasse exceção, o sistema poderia quebrar.

**Teste que revelou:**  
`deve_RetornarListaVazia_Quando_CatalogoLancaExcecao`

**Correção aplicada:**  
O `RecomendadorService` passou a tratar exceções e retornar lista vazia.

---

## Bug 5 — Notificação poderia ser enviada sem recomendação

**Descrição:**  
O sistema poderia tentar notificar o usuário mesmo quando nenhuma recomendação fosse gerada.

**Teste que revelou:**  
`deve_RetornarListaVazia_Quando_CatalogoLancaExcecao`

**Correção aplicada:**  
O notificador só é chamado quando a lista de recomendações não está vazia.
