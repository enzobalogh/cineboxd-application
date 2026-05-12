# Falas prontas para apresentação ao professor

## 1. Abertura

“Professor, nosso projeto é um recomendador de filmes por perfil. O usuário informa preferências como gêneros favoritos, duração ideal, classificação etária máxima, idiomas aceitos, histórico de filmes assistidos e notas dadas. A partir disso, o sistema consulta um catálogo, filtra filmes incompatíveis, calcula um score de compatibilidade e devolve uma lista ranqueada.”

## 2. Arquitetura

“A arquitetura foi separada em quatro pacotes principais: model, service, exception e util.”

### model

“No pacote model ficam as classes que representam o domínio do sistema. Por exemplo, Filme representa o filme do catálogo, PerfilCinefilo representa as preferências do usuário, Usuario representa quem recebe as recomendações e Recomendacao representa o resultado final com filme, score e justificativa.”

### service

“No pacote service ficam as regras de negócio. O RecomendadorService é o orquestrador: ele chama o catálogo, chama o filtro, chama a calculadora de score, ordena os resultados, registra no histórico e chama o notificador quando necessário.”

### exception

“No pacote exception colocamos exceções específicas, como PesoInvalidoException, DuracaoInvalidaException e NotaInvalidaException. Isso deixa o erro mais claro do que usar RuntimeException genérica.”

### util

“No pacote util colocamos o GeradorAleatorio. Ele foi separado como interface para que a aleatoriedade possa ser mockada nos testes.”

## 3. Diagrama de sequência

“O fluxo começa quando o usuário pede recomendações. O RecomendadorService busca os filmes no CatalogoFilmesAPI. Depois, o FiltroFilmes remove os filmes incompatíveis, como filmes já assistidos, filmes acima da classificação máxima, idioma não aceito ou gênero com peso zero. Em seguida, a CalculadoraScore calcula a pontuação dos filmes restantes. O service ordena a lista, registra no HistoricoUsuarioRepository e, se as notificações estiverem ativas, chama o NotificadorPush.”

## 4. Testes JUnit puros

“Nos testes puros com JUnit, testamos classes que possuem lógica própria e não dependem de serviços externos. Por exemplo, PerfilCinefilo, FiltroFilmes e CalculadoraScore.”

### Teste de peso inválido

“Usamos assertThrows para verificar se o sistema lança PesoInvalidoException quando alguém tenta cadastrar peso maior que 1.0. Esse teste protege a regra de que os pesos devem ficar entre 0.0 e 1.0.”

### Teste de filme já assistido

“Usamos assertFalse para garantir que um filme presente no histórico não aparece na lista filtrada. Essa é uma regra central do sistema, porque não faz sentido recomendar algo que o usuário já assistiu.”

### Teste de score

“Usamos assertTrue para verificar que um filme de gênero preferido recebe score alto. Esse teste valida a regra principal da CalculadoraScore.”

## 5. Testes com Mockito

“Nos testes com Mockito, mockamos dependências externas ou instáveis.”

### CatalogoFilmesAPI

“Mockamos o catálogo porque em produção ele poderia vir de uma API externa. No teste, usamos when().thenReturn() para controlar exatamente quais filmes são retornados.”

### HistoricoUsuarioRepository

“Mockamos o histórico porque ele representa persistência em banco de dados. Usamos verify() para garantir que registrarRecomendacao foi chamado após uma recomendação.”

### NotificadorPush

“Mockamos o notificador porque ele representa um serviço externo de push. Assim, o teste não envia notificação real.”

### GeradorAleatorio

“Mockamos o GeradorAleatorio porque aleatoriedade deixa teste instável. Com mock, conseguimos definir qual índice será sorteado.”

## 6. ArgumentCaptor

“Usamos ArgumentCaptor para capturar a lista enviada ao histórico. O verify apenas confirma que o método foi chamado, mas o captor permite olhar o conteúdo exato que foi passado para o mock. Isso torna o teste mais forte.”

## 7. Bugs encontrados

“Um bug encontrado foi que o sistema poderia aceitar peso maior que 1.0. O teste com assertThrows revelou isso, e a correção foi adicionar a validação no PerfilCinefilo.”

“Outro bug foi que filme já assistido ainda poderia aparecer na recomendação. O teste do FiltroFilmes revelou esse problema, e corrigimos verificando o histórico antes de aceitar o filme.”

“Também tratamos o caso de catálogo vazio e falha da API, garantindo que o sistema retorne lista vazia em vez de null ou erro bruto.”

## 8. Reflexão crítica

“A principal decisão foi separar o que deve ser mockado do que deve ser testado de verdade. Mockamos API, banco, notificação e aleatoriedade. Não mockamos Filme, PerfilCinefilo, FiltroFilmes e CalculadoraScore, porque eles fazem parte da lógica real do sistema.”

“A gente teve dúvida se deveria mockar a CalculadoraScore dentro do RecomendadorService, mas decidimos usar a instância real porque ela representa uma regra central. Se fosse mockada, poderíamos esconder erro na fórmula.”

## 9. Encerramento

“Mesmo sendo uma versão parcial, o projeto já tem a estrutura principal, fluxo de recomendação, testes puros, testes com Mockito, ArgumentCaptor e reflexão crítica sobre as dependências. A próxima etapa seria ampliar o catálogo, melhorar as justificativas e aumentar a cobertura.”
