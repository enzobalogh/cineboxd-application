# Lista de cenários de teste

| ID | Cenário | Entrada | Resultado esperado | Status |
|---|---|---|---|---|
| T01 | Criar perfil com peso válido | Gênero Drama com peso 0.8 | Perfil aceita o peso | Feito |
| T02 | Criar perfil com peso inválido | Gênero Ação com peso 1.5 | Lança PesoInvalidoException | Feito |
| T03 | Criar perfil com peso negativo | Gênero Drama com peso -0.2 | Lança PesoInvalidoException | Planejado |
| T04 | Criar perfil com duração inválida | Duração mínima 160 e máxima 90 | Lança DuracaoInvalidaException | Feito |
| T05 | Adicionar nota válida | Filme F01 com nota 5 | Nota é salva no perfil | Feito |
| T06 | Adicionar nota inválida | Filme F01 com nota 6 | Lança NotaInvalidaException | Feito |
| T07 | Marcar filme como assistido | Filme F01 | Filme aparece no histórico | Feito |
| T08 | Criar filme com atributos completos | Filme com título, ano, duração, gênero, idioma, classificação e popularidade | Filme é criado corretamente | Feito |
| T09 | Comparar filmes com mesmo ID | Dois filmes com ID F01 | São considerados iguais | Feito |
| T10 | Filtrar filme já assistido | Histórico contém A Chegada | A Chegada é removido | Feito |
| T11 | Filtrar classificação acima da máxima | Perfil aceita 16, filme é 18 | Filme é removido | Feito |
| T12 | Filtrar idioma não aceito | Perfil aceita Português/Inglês, filme é Francês | Filme é removido | Feito |
| T13 | Filtrar gênero com peso zero | Terror com peso 0.0 | Filme de terror é removido | Feito |
| T14 | Catálogo vazio no filtro | Lista vazia | Retorna lista vazia, não null | Feito |
| T15 | Score alto para gênero preferido | Perfil gosta de Ficção Científica | Score fica alto | Feito |
| T16 | Score baixo para gênero pouco preferido | Perfil quase não gosta de Romance | Score fica menor | Planejado |
| T17 | Duração dentro da faixa | Filme com 116 minutos | Componente duração vale 100 | Feito |
| T18 | Duração fora da faixa | Filme com 180 minutos | Score de duração reduzido | Feito |
| T19 | Recomendação respeita topN | topN = 1 | Retorna no máximo 1 recomendação | Feito |
| T20 | Recomendações ordenadas por score | Vários filmes com scores diferentes | Maior score vem primeiro | Planejado |
| T21 | Catálogo lança exceção | API offline | Sistema retorna lista vazia | Feito |
| T22 | Histórico é chamado após recomendar | Recomendação gerada | registrarRecomendacao é chamado | Feito |
| T23 | Notificação ligada | Usuário com push ativo | NotificadorPush é chamado | Feito |
| T24 | Notificação desligada | Usuário com push desativado | NotificadorPush não é chamado | Feito |
| T25 | Modo Surpreenda-me | Lista filtrada com filmes válidos | Retorna um filme aleatório válido | Feito |
