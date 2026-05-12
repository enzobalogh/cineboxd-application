# Reflexão crítica

## Dependências mockadas

| Dependência | Motivo |
|---|---|
| CatalogoFilmesAPI | Representa uma API externa de filmes. No teste, não devemos depender de internet nem de dados variáveis. |
| HistoricoUsuarioRepository | Representa persistência em banco de dados. Teste unitário não deve gravar dados reais. |
| NotificadorPush | Representa serviço externo de notificação. Teste não deve enviar push real. |
| GeradorAleatorio | Aleatoriedade deixa testes instáveis. Com mock, o resultado fica previsível. |

## O que não foi mockado

| Classe | Motivo |
|---|---|
| Filme | É objeto de domínio simples. |
| PerfilCinefilo | É objeto de domínio com validações importantes. |
| Recomendacao | É o resultado real do sistema. |
| FiltroFilmes | Contém regra de negócio pura e deve ser testado de verdade. |
| CalculadoraScore | Contém a fórmula principal do sistema e deve ser testada de verdade. |
| Enums | São valores fixos; não faz sentido mockar. |

## Dúvida entre mock e objeto real

A principal dúvida foi na `CalculadoraScore` e no `FiltroFilmes`.  
No começo, parecia possível mockar essas classes dentro do `RecomendadorService`, mas decidimos usar objetos reais porque elas fazem parte da lógica central do sistema.

Se a `CalculadoraScore` fosse mockada, o teste poderia passar mesmo com a fórmula errada.  
Se o `FiltroFilmes` fosse mockado, o teste poderia esconder erro nas regras de exclusão.

Por isso, usamos mock apenas para dependências externas ou instáveis.
