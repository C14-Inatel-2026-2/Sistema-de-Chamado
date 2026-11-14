# Sistema de Chamados de Manutenção

Projeto desenvolvido para a disciplina **C14 – Engenharia de Software**, do Instituto Nacional de Telecomunicações – INATEL.

## Descrição

O **Sistema de Chamados de Manutenção** é uma aplicação web desenvolvida para centralizar o registro, acompanhamento e gerenciamento de solicitações de manutenção.

O sistema permite cadastrar usuários com diferentes perfis, registrar locais e equipamentos, abrir chamados de manutenção, acompanhar o atendimento técnico, controlar a evolução dos status e consultar o histórico das alterações realizadas.

A proposta é facilitar a comunicação entre solicitantes, técnicos e responsáveis pelo sistema, permitindo maior organização e rastreabilidade dos serviços de manutenção.

---

## Objetivo

Desenvolver uma aplicação capaz de gerenciar o ciclo completo de um chamado de manutenção, desde sua abertura até seu encerramento, mantendo informações sobre:

- usuários;
- locais;
- equipamentos;
- chamados;
- prioridade;
- atendimento técnico;
- status;
- histórico de alterações.

---

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- Maven
- PostgreSQL
- H2 Database
- JUnit 5
- Mockito
- JaCoCo
- Jenkins
- Docker
- Git
- GitHub
- Jira

---

# Gestão Ágil

O projeto utiliza a metodologia ágil **Scrum**.

As atividades são organizadas no Jira utilizando:

- Product Backlog;
- User Stories;
- critérios de aceite;
- Sprint Backlog;
- quadro Scrum;
- acompanhamento de status das atividades.

As User Stories detalhadas, seus responsáveis e seus respectivos critérios de aceite podem ser consultados no Jira do projeto.

### Jira

https://ppedrocesarmaiaa.atlassian.net/jira/software/projects/SCM/boards/1/backlog

---

# Product Backlog

## US01 – Cadastro de Usuários

**Responsável:** Eduardo Nogueira Belarmino

**Como responsável pelo sistema,**  
quero cadastrar novos usuários informando seus dados pessoais e de acesso,  
para permitir que diferentes usuários tenham acesso ao sistema de chamados de manutenção de acordo com seus respectivos perfis.

### Principais critérios

- O formulário deve possuir nome, e-mail, senha e perfil.
- Todos os campos obrigatórios devem ser preenchidos.
- O sistema deve salvar usuários com dados válidos.
- Não deve ser permitido cadastrar dois usuários utilizando o mesmo e-mail.
- O sistema deve informar erros de validação quando o cadastro não puder ser realizado.

---

## US02 – Abertura e Consulta de Chamados

**Responsável:** Pedro Henrique dos Santos Araujo

**Como solicitante,**  
quero abrir chamados de manutenção e consultá-los,  
para que a equipe técnica saiba qual problema precisa ser resolvido e eu possa acompanhar o andamento.

### Principais critérios

- O chamado deve possuir título, descrição e prioridade.
- Título e descrição são obrigatórios.
- A prioridade deve ser `BAIXA`, `MEDIA` ou `ALTA`.
- Todo novo chamado deve iniciar com status `ABERTO`.
- A data e hora de abertura devem ser registradas automaticamente.
- O sistema deve listar todos os chamados cadastrados.
- Deve ser possível consultar um chamado pelo seu identificador.
- Caso o chamado não exista, o sistema deve informar que ele não foi encontrado.
- O chamado pode ficar vinculado ao solicitante e ao local/equipamento relacionado ao problema.

### Endpoints relacionados

```text
POST /chamados
GET /chamados
GET /chamados/{id}
```

Os vínculos com solicitante, local e equipamento são opcionais e informados pelo ID de registros já existentes:

```json
{
  "titulo": "Projetor não liga",
  "descricao": "Sem imagem",
  "prioridade": "ALTA",
  "solicitante": {
    "id": 1
  },
  "local": {
    "id": 1
  }
}
```

Respostas:

- `201` ao criar;
- `400` para dados inválidos ou vínculo inexistente;
- `404` quando o chamado não existe.

---

## US03 – Atendimento Técnico

**Responsável:** Felipe da Silva Inácio

**Como técnico de manutenção,**  
quero visualizar os chamados atribuídos a mim e atualizar seus status durante o atendimento,  
para acompanhar e organizar os serviços de manutenção sob minha responsabilidade.

### Principais critérios

- O técnico deve conseguir visualizar seus chamados atribuídos.
- O chamado deve apresentar informações como título, descrição e prioridade.
- O técnico deve poder iniciar o atendimento.
- Um chamado `ABERTO` pode ser alterado para `EM_ANDAMENTO`.
- Um chamado `EM_ANDAMENTO` pode ser alterado para `RESOLVIDO`.
- Um chamado resolvido pode ser alterado para `FECHADO`.
- O sistema deve impedir transições que não respeitem o fluxo estabelecido.
- Tentativas de alteração inválida devem ser informadas ao usuário.

### Fluxo de status

```text
ABERTO
   ↓
EM_ANDAMENTO
   ↓
RESOLVIDO
   ↓
FECHADO
```

---

## US04 – Cadastro de Locais e Equipamentos

**Responsável:** Cauã de Souza Flauzino

**Como administrador do sistema,**  
quero cadastrar os locais físicos e os equipamentos da instituição,  
para que os solicitantes possam indicar com precisão onde e em qual máquina o problema ocorreu ao abrir um chamado.

### Cadastro de locais

- O nome do local é obrigatório.
- Não deve ser permitido cadastrar dois locais com exatamente o mesmo nome.
- Deve ser possível criar e listar locais cadastrados.

### Cadastro de equipamentos

- O equipamento deve possuir nome ou descrição.
- O equipamento deve possuir uma identificação.
- Cada equipamento deve estar associado a um local existente.
- O sistema deve recusar o cadastro caso o local informado não exista.
- Dados inválidos não devem ser salvos.

### Endpoints relacionados

```text
POST /locais
GET /locais

POST /equipamentos
GET /equipamentos
```

---

## US05 – Histórico e Filtros de Chamados

**Responsável:** Allan Pinto Nogueira

**Como usuário do sistema,**  
quero consultar o histórico e filtrar os chamados,  
para acompanhar as alterações realizadas e localizar chamados com maior facilidade.

### Principais critérios

- Alterações realizadas em um chamado devem ser registradas.
- O histórico deve permitir identificar o status anterior e o novo status.
- Deve ser possível identificar o usuário responsável pela alteração.
- A data da alteração deve ser registrada.
- O usuário deve conseguir consultar o histórico de um chamado.
- O sistema deve permitir a utilização de filtros para facilitar a localização dos chamados.
- Quando nenhum chamado corresponder ao filtro utilizado, o sistema deve retornar uma lista vazia ou informar que nenhum resultado foi encontrado.

---

## US06 – Dashboard Inicial

**Responsável:** Pedro Cesar Maia

**Como usuário do sistema,**  
quero acessar um dashboard inicial,  
para visualizar a página principal do Sistema de Chamados de Manutenção.

### Principais critérios

- Ao acessar `/dashboard`, o sistema deve apresentar a página principal.
- O dashboard deve apresentar o título `Sistema de Chamados de Manutenção`.
- Ao acessar `/`, o usuário também deve visualizar o dashboard.

### Rotas relacionadas

```text
/
/dashboard
```

**Issue Jira:** SCM-7  
**Status final:** Entregue

### Implementação

- `PaginaController.java`
- `dashboard.html`

### Testes

- `PaginaControllerTest.java`

**Pull Request:** [#38](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/38)


---

# Requisitos Funcionais

## Usuários

**RF01** – O sistema deve permitir o cadastro de usuários.

**RF02** – O cadastro deve possuir nome, e-mail, senha e perfil.

**RF03** – O sistema deve validar os campos obrigatórios do cadastro de usuários.

**RF04** – O sistema deve impedir o cadastro de usuários com e-mails já utilizados.

---

## Chamados

**RF05** – O sistema deve permitir que um solicitante abra um chamado de manutenção.

**RF06** – O chamado deve possuir título, descrição e prioridade.

**RF07** – A prioridade deve aceitar os valores `BAIXA`, `MEDIA` ou `ALTA`.

**RF08** – Um novo chamado deve receber automaticamente o status `ABERTO`.

**RF09** – A data e hora de abertura devem ser registradas automaticamente.

**RF10** – O sistema deve permitir listar os chamados cadastrados.

**RF11** – O sistema deve permitir consultar um chamado pelo seu identificador.

**RF12** – O sistema deve informar quando um chamado solicitado não existir.

**RF13** – O chamado deve poder ser associado ao solicitante, ao local e ao equipamento relacionados.

---

## Atendimento Técnico

**RF14** – O sistema deve permitir a atribuição de chamados a técnicos.

**RF15** – O técnico deve conseguir visualizar seus chamados atribuídos.

**RF16** – O técnico deve poder atualizar o status dos chamados durante o atendimento.

**RF17** – O sistema deve respeitar o seguinte fluxo:

```text
ABERTO → EM_ANDAMENTO → RESOLVIDO → FECHADO
```

**RF18** – O sistema deve impedir transições de status inválidas.

---

## Locais

**RF19** – O sistema deve permitir cadastrar locais.

**RF20** – O nome do local deve ser obrigatório.

**RF21** – O sistema deve impedir o cadastro de locais com o mesmo nome.

**RF22** – O sistema deve permitir listar os locais cadastrados.

---

## Equipamentos

**RF23** – O sistema deve permitir cadastrar equipamentos.

**RF24** – O equipamento deve possuir nome ou descrição e identificação.

**RF25** – O equipamento deve possuir identificação adequada para sua consulta.

**RF26** – Todo equipamento deve estar associado a um local existente.

**RF27** – O sistema deve permitir listar os equipamentos cadastrados.

---

## Histórico e Filtros

**RF28** – O sistema deve registrar as alterações realizadas nos chamados.

**RF29** – O histórico deve armazenar o status anterior e o novo status.

**RF30** – O histórico deve registrar o usuário responsável e a data da alteração.

**RF31** – O sistema deve permitir consultar o histórico de um chamado.

**RF32** – O sistema deve permitir filtrar chamados.

---

## Interface Web

**RF33** – O sistema deve possuir uma interface web para interação com o usuário.

**RF34** – O sistema deve disponibilizar um dashboard inicial.

**RF35** – O dashboard deve estar disponível nas rotas `/` e `/dashboard`.

---

# Requisitos Não Funcionais

**RNF01** – O sistema deve utilizar **Java 21** como linguagem principal.

**RNF02** – O gerenciamento de dependências e construção do projeto deve ser realizado utilizando **Maven**.

**RNF03** – A aplicação deve utilizar **Spring Boot**.

**RNF04** – A persistência dos dados deve utilizar **Spring Data JPA**.

**RNF05** – A interface web deve utilizar **Thymeleaf**.

**RNF06** – O sistema deve utilizar banco de dados relacional.

**RNF07** – O projeto deve possuir testes automatizados utilizando **JUnit 5**.

**RNF08** – Dependências externas devem poder ser simuladas nos testes utilizando **Mockito**.

**RNF09** – A cobertura dos testes deve ser analisada utilizando **JaCoCo**.

**RNF10** – O projeto deve possuir um pipeline de integração e entrega contínua utilizando **Jenkins**.

**RNF11** – O código-fonte deve ser versionado utilizando **Git e GitHub**.

**RNF12** – O desenvolvimento deve utilizar branches e Pull Requests para integração das alterações.

**RNF13** – A aplicação deve permitir execução por meio de container Docker para publicação externa.

---

# Testes

Os testes automatizados do projeto podem ser executados com:

```bash
mvn test
```

Para realizar a validação completa do projeto:

```bash
mvn clean verify
```

O relatório de cobertura do JaCoCo pode ser gerado durante a execução configurada no projeto.

Quando disponível, o relatório pode ser encontrado em:

```text
target/site/jacoco/
```

---

# Docker e Deploy Externo

O projeto foi preparado para publicação externa utilizando Docker.

Foi criado um arquivo `Dockerfile` na raiz do projeto para realizar o build da aplicação utilizando Maven e Java 21 e executar o arquivo JAR gerado pelo Spring Boot.

No arquivo `application.properties` foi adicionada a configuração:

```properties
server.port=${PORT:8080}
```

Essa configuração permite que a aplicação continue utilizando a porta `8080` durante a execução local e também aceite a porta fornecida pelo ambiente de hospedagem durante o deploy externo.

A plataforma escolhida para o deploy externo é o **Render**.

**Status:** configuração do serviço externo em andamento.

**URL pública:** [PREENCHER APÓS CONFIGURAR O RENDER]

Após a configuração do serviço externo, o Jenkins será integrado ao Render para permitir que o pipeline solicite novos deploys automaticamente.

---

# CI/CD

O projeto utiliza **Jenkins** para automatizar o processo de integração e entrega contínua.

O pipeline é definido através do arquivo:

```text
Jenkinsfile
```

O fluxo do pipeline contempla etapas como:

```text
Validação
   ↓
Build
   ↓
Testes / Cobertura
   ↓
Relatório
   ↓
Package
   ↓
Deploy
```

A integração com o deploy externo será finalizada após a configuração e validação do serviço no Render.

---

# Organização do Projeto

A aplicação segue uma estrutura baseada em camadas:

```text
src/
├── main/
│   ├── java/
│   │   └── br/inatel/chamados/
│   │       ├── controller/
│   │       ├── model/
│   │       ├── repository/
│   │       └── service/
│   │
│   └── resources/
│       ├── templates/
│       ├── static/
│       └── application.properties
│
└── test/
    └── java/
```

---

# Execução do Projeto

Para executar a aplicação utilizando Maven:

```bash
mvn spring-boot:run
```

Após iniciar a aplicação, a interface pode ser acessada em:

```text
http://localhost:8080/
```

---

# Refactorings

Os refactorings registrados abaixo foram aplicados para melhorar a estrutura interna do código sem alterar seu comportamento externo.

## Cauã de Souza Flauzino

| # | Smell | Refactoring aplicado | Por quê | PR |
|---|-------|----------------------|---------|----|
| 1 | Field Injection (`@Autowired` em campo) em `LocalController` e `EquipamentoController` | Replace Field Injection with Constructor Injection | As dependências ficam explícitas e imutáveis, e o controller pode ser criado em testes sem o Spring | [#30](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/30) |
| 2 | Controller acessando o repository direto, sem camada de service | Extract Class (`LocalService` e `EquipamentoService`) | Separa a camada web do acesso a dados, segue o padrão do projeto e permite testes unitários com Mockito | [#31](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/31) |

---

## Eduardo Nogueira

| # | Smell | Refactoring aplicado | Por quê | PR |
|---|-------|----------------------|---------|----|
| 1 | Mensagem de erro de "email já cadastrado" escrita diretamente na exception em `UsuarioService` | Create Constant String (`EMAIL_JA_CADASTRADO`) | Centraliza a mensagem em uma constante, facilitando a manutenção e evitando strings repetidas no código | [#28](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/28/changes/32f81f5ae4de036c2b8973b70fc38b1c6ee016f6) |
| 2 | Parâmetro `Model` não utilizado no método `listar()` de `UsuarioController` | Remove Parameter | Remove um parâmetro desnecessário, simplificando a assinatura do método e evitando dependências que não são utilizadas | [#28](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/28/changes/0e935c72b53a337361adb77bfe34cd3626acb1ba) |

---

## Pedro Henrique dos Santos Araujo

| # | Smell | Refactoring aplicado | Por quê | PR |
|---|-------|----------------------|---------|----|
| 1 | Uso desajeitado de `Optional` (`isEmpty()` seguido de `get()`) em `ChamadoConsultaService.buscarPorId` | Replace with `orElseThrow` | Remove o `get()` inseguro e deixa o método mais curto e legível, sem mudar a exceção nem a mensagem | [#27](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/27) |
| 2 | Código duplicado nos testes: criação do `ChamadoConsultaService` repetida em cada teste de `ChamadoConsultaServiceTest` | Extract setup para `@BeforeEach` | Remove a repetição e facilita acrescentar novos testes sem copiar o setup | [#29](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/29) |

---

## Allan Pinto Nogueira

### Refactoring 1

- **Code smell:** duplicação da validação de título e descrição em `ChamadoService.abrirChamado`, com a mesma verificação de texto nulo ou em branco.
- **Refactoring aplicado:** Remove Duplicate Code com Extract Method, criando o método privado `validarTextoObrigatorio`.
- **Justificativa:** centralizar a validação repetida, preservando a ordem das verificações, as mensagens de erro e os dados do chamado.
- **Pull Request:** https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/32

### Refactoring 2

- **Code smell:** expressão condicional com comparações repetidas e operadores `&&` e `||` em `ChamadoService.transicaoPermitida`.
- **Refactoring aplicado:** Simplify Conditional Expression, substituindo a expressão por um `switch` sobre o status atual.
- **Justificativa:** explicitar o destino permitido para cada status, mantendo somente as transições `ABERTO → EM_ANDAMENTO → RESOLVIDO → FECHADO` e a rejeição de status atual nulo.
- **Pull Request:** https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/32

### Validação

Validação dos refactorings antes da integração da `main`, em 13/11/2026, com Java 21 e `mvn clean test`:

| Estado | Testes | Falhas | Erros | Ignorados | Resultado |
| --- | --- | --- | --- | --- | --- |
| Antes dos refactorings | 30 | 0 | 0 | 0 | BUILD SUCCESS |
| Após o refactoring 1 | 40 | 0 | 0 | 0 | BUILD SUCCESS |
| Após o refactoring 2 | 65 | 0 | 0 | 0 | BUILD SUCCESS |

Os novos testes passaram também antes de cada refactoring correspondente. Eles verificam as validações de abertura, as 16 combinações de status e os casos nulos, incluindo mensagens de erro e ausência de persistência nas rejeições.

O JaCoCo 0.8.13 gerou o relatório em:

```text
target/site/jacoco/index.html
```

A cobertura global de linhas passou de 65,78% para 70,47%, e a cobertura de ramificações passou de 70,59% para 96,55%.

Após integrar a `main` para resolver o conflito do PR #32, `mvn clean test` aprovou 87 testes, sem falhas, erros ou testes ignorados.

O JaCoCo gerou relatório com cobertura global de linhas de 70,65% e cobertura de ramificações de 67,52%.

Esses resultados incluem o código e os testes adicionados pelos demais integrantes na `main`.

---

## Pedro Cesar Maia

### Refactoring 1 – Remoção de duplicação nas rotas do dashboard

**Code smell:** código duplicado.

Anteriormente, os métodos responsáveis pelas rotas `/` e `/dashboard` realizavam a mesma preparação do `Model` e retornavam a mesma página.

**Refactoring aplicado:** Remove Duplicate Code.

O método `inicio()` passou a reutilizar o método `dashboard()`:

```java
@GetMapping("/")
public String inicio(Model model) {
    return dashboard(model);
}
```

**Motivo:** reduzir duplicação de código e manter apenas um ponto responsável pela preparação e retorno do dashboard.

**Pull Request:** [#38](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/38)

### Refactoring 2 – Renomeação do método de preparação do Model

**Code smell:** nome de método pouco específico.

O método:

```text
prepararModel()
```

foi renomeado para:

```text
adicionarTituloAoModel()
```

**Refactoring aplicado:** Rename Method.

**Motivo:** deixar mais claro que a responsabilidade do método é adicionar o título da aplicação ao `Model`, sem alterar o comportamento externo da aplicação.

**Pull Request:** [#38](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/38)


### Validação dos refactorings

Após as alterações foi executado:

```bash
mvn clean verify
```

Resultado:

```text
BUILD SUCCESS
```

Os testes de `PaginaControllerTest` verificam:

- acesso à rota `/` utilizando Mockito;
- acesso à rota `/dashboard` utilizando Mockito;
- acesso à rota `/` utilizando `ExtendedModelMap`;
- acesso à rota `/dashboard` utilizando `ExtendedModelMap`;
- comportamento da rota `/` quando o `Model` é nulo;
- comportamento da rota `/dashboard` quando o `Model` é nulo.

---

# Uso de IA

Cada integrante declara abaixo como utilizou ferramentas de Inteligência Artificial durante o desenvolvimento.

## Pedro Henrique dos Santos Araujo

**Modelo:** Claude Sonnet 5.5 (Anthropic), usado em todas as sessões.

**Para que foi usado:**

- Orientação passo a passo de cada entrega.
- Escrever e revisar código do módulo.
- Auxílio na elaboração de testes unitários.
- Redigir mensagens de commit, descrições de PR e textos do Jira.
- Revisar refactorings e Pull Requests.

### Prompt 1 – Definir o conjunto de testes

**Contexto:** a entrega exigia testes unitários dos arquivos de cada integrante.

**Prompt:**

> "Proponha 4 testes unitários apenas para os arquivos do meu módulo: 2 com mock e 2 sem mock, incluindo pelo menos 1 teste negativo, seguindo boas práticas de nomenclatura e estrutura (given/when/then)."

**Resultado:** **aceito com ajustes.** Os testes foram adaptados ao `ChamadoTest` já existente na `main`.

### Prompt 2 – Validar se um refactoring é adequado

**Contexto:** os slides da disciplina definem o que conta e o que não conta como refactoring.

**Prompt:**

> "Com base nos critérios dos slides em anexo, avalie se cada um dos meus dois refactorings é adequado. Aponte qual deles pode ser considerado apenas formatação e sugira uma alternativa no mesmo arquivo."

**Resultado:** **ajustado.** Um refactoring inicialmente proposto foi substituído por uma extração de setup para `@BeforeEach`.

### Prompt 3 – Complementar o stage de build do pipeline

**Contexto:** o `Jenkinsfile` já possuía um stage básico de build.

**Prompt:**

> "Complemente o stage Build do Jenkinsfile existente, sem substituir os demais stages: limpar o target antes de compilar e falhar o stage se o JAR esperado não for gerado. O agente é Windows, então use bat."

**Resultado:** **aceito.**

### Prompt 4 – Tratar erro encontrado no teste manual

**Contexto:** um chamado com vínculo inválido devolvia erro 500.

**Prompt:**

> "O POST /chamados com um local inexistente retorna 500 com stack trace. Proponha um tratamento no controller que devolva 400 com uma mensagem curta, e o teste unitário correspondente."

**Resultado:** **ajustado após testar.**

### Dinâmica de uso

As conversas foram realizadas em etapas curtas, com revisão e execução local antes de cada commit.

### O que foi feito sem IA

- execução dos comandos Git;
- gerenciamento da branch;
- commits;
- push;
- Pull Requests;
- execução da aplicação;
- execução dos testes;
- testes manuais;
- decisão dos refactorings utilizados;
- revisão final do código.

### Compreensão

O código sugerido foi lido, testado e revisado antes da utilização.

---

## Pedro Cesar Maia

**Modelo:** ChatGPT — GPT-5.6 Sol (OpenAI).

**Para que foi utilizado:**

- apoio na implementação e revisão da interface web;
- revisão do `PaginaController`;
- criação e revisão dos testes unitários;
- análise dos requisitos da NP2;
- identificação e documentação dos refactorings;
- revisão da documentação do projeto;
- apoio na organização das atividades relacionadas ao Jira;
- interpretação dos requisitos de CI/CD;
- apoio na preparação do Docker e do deploy externo.

### Prompt 1 – Análise dos requisitos da NP2

**Contexto:** verificar se o projeto atendia aos requisitos necessários para a entrega final da NP2.

**Prompt organizado a partir da solicitação real:**

> “Analise os requisitos da NP2 e compare com o estado atual do Sistema de Chamados de Manutenção. Verifique se faltam testes, documentação, refactorings, requisitos de CI/CD ou outros itens importantes para a entrega final.”

**Resultado:** **aceito com ajustes.** A análise ajudou a identificar a necessidade de documentar os refactorings, ampliar os testes relacionados ao dashboard, registrar o uso de IA e preparar o deploy externo.

### Prompt 2 – Refactoring do PaginaController

**Contexto:** a entrega final exigia a realização de pelo menos dois refactorings por integrante.

**Prompt organizado a partir da solicitação real:**

> “Analise o PaginaController e proponha dois refactorings simples que melhorem a estrutura interna sem modificar o comportamento das rotas `/` e `/dashboard`.”

**Resultado:** **aceito.** Foram aplicados os refactorings Remove Duplicate Code e Rename Method.

### Prompt 3 – Revisão dos testes do dashboard

**Contexto:** verificar se os testes existentes da US06 poderiam ser ampliados com cenários relevantes.

**Prompt organizado a partir da solicitação real:**

> “Revise os testes existentes do PaginaController e verifique se vale adicionar novos casos relacionados às rotas `/` e `/dashboard`, utilizando Mockito, Model real e cenários negativos.”

**Resultado:** **aceito com ajustes.** A classe `PaginaControllerTest` passou a possuir seis cenários de teste relacionados diretamente ao comportamento das rotas do dashboard.

### Prompt 4 – Preparação para deploy externo

**Contexto:** a entrega final exige que a aplicação seja publicada fora da máquina utilizada pelo Jenkins.

**Prompt organizado a partir da solicitação real:**

> “Analise o projeto Spring Boot e indique como prepará-lo para publicação externa utilizando Docker e Render, sem alterar as funcionalidades existentes.”

**Resultado:** **aceito com ajustes.** Foi criado um `Dockerfile` e adicionada ao `application.properties` a configuração da porta através da variável de ambiente `PORT`.

### Dinâmica de uso

A IA foi utilizada como ferramenta de apoio durante o desenvolvimento.

As sugestões fornecidas foram analisadas antes de serem incorporadas ao projeto.

O código e a documentação sugeridos foram conferidos e validados antes dos commits.

### O que foi feito sem IA

Foram realizados diretamente por mim:

- execução dos comandos Git;
- gerenciamento da branch `PedroCesar`;
- realização de commits e push;
- execução dos testes;
- execução do comando `mvn clean verify`;
- organização e acompanhamento das atividades no Jira;
- conferência das alterações realizadas;
- criação e acompanhamento dos Pull Requests;
- decisão final sobre quais sugestões seriam incorporadas ao projeto.

### Compreensão

Todo código, teste, refactoring e documentação produzidos com apoio de IA foram revisados antes de sua utilização no projeto.

---

## Eduardo Nogueira

**Modelo:** ChatGPT — GPT-6 (OpenAI).

**Para que foi utilizado:**

- apoio na implementação do cadastro e da autenticação de usuários;
- revisão do `UsuarioController` e do `UsuarioService`;
- auxílio na implementação da criptografia de senhas com BCrypt;
- identificação e correção de erros relacionados ao login e ao redirecionamento de páginas;
- sugestões e aplicação de refactorings;
- interpretação de falhas nos testes automatizados;
- apoio na documentação dos refactorings;
- auxílio na elaboração de mensagens de commit e descrições de Pull Requests;
- orientação sobre comandos Git e gerenciamento de branches.

### Prompt 1 – Implementação da autenticação de usuários

**Contexto:** implementar o processo de login dos usuários, verificando as credenciais informadas e gerenciando a sessão.

**Prompt:**

> "Como implementar a autenticação de usuários utilizando e-mail e senha no UsuarioService e UsuarioController, incluindo a validação das credenciais e o gerenciamento da sessão?"

**Resultado:** **aceito com ajustes.** As orientações auxiliaram na implementação da autenticação, na validação das credenciais e no armazenamento das informações do usuário na sessão.

### Prompt 2 – Depuração do redirecionamento após o login

**Contexto:** após realizar o login, a aplicação apresentava um erro 404 relacionado à página `dashboard`.

**Prompt:**

> "Ao fazer login, aparece uma Whitelabel Error Page com status 404 e a mensagem No static resource dashboard. Como corrigir o redirecionamento para a página correta?"

**Resultado:** **aceito com ajustes.** A análise ajudou a identificar problemas relacionados às rotas e aos redirecionamentos da aplicação. O fluxo foi ajustado conforme as páginas disponíveis no projeto.

### Prompt 3 – Refatorações no código

**Contexto:** a entrega exigia pelo menos dois refactorings por integrante, com melhorias na estrutura interna do código.

**Prompt:**

> "Analise o código e sugira dois refactorings simples que melhorem a legibilidade e a organização, sem modificar o comportamento esperado."

**Resultado:** **aceito.** Foram aplicados dois refactorings:
- Extração da mensagem de erro de e-mail duplicado para a constante `EMAIL_JA_CADASTRADO` no `UsuarioService`.
- Remoção do parâmetro `Model` não utilizado no método `listar()` do `UsuarioController`.

### Dinâmica de uso

A IA foi utilizada como ferramenta de consulta e apoio durante o desenvolvimento da parte de gerenciamento de usuários e autenticação.

As sugestões de código foram analisadas e adaptadas conforme a estrutura existente no projeto. As alterações foram acompanhadas por testes e revisões antes de serem registradas em commits e enviadas por pull request.

### O que foi feito sem IA

- execução dos comandos Git;
- gerenciamento da branch `EduardoNogueira`;
- realização de commits e push;
- execução dos testes automatizados com Maven;
- análise dos resultados apresentados pelo ambiente de desenvolvimento;
- conferência das alterações realizadas nos arquivos;
- criação e acompanhamento do Pull Request;
- decisão final sobre quais sugestões seriam incorporadas ao projeto;
- validação das alterações na implementação de usuários e autenticação.

### Compreensão

Durante o processo, foi necessário compreender as sugestões recebidas da IA, verificar sua compatibilidade com a arquitetura do projeto e realizar adaptações quando necessário.

---

## Acesso para testes

Para facilitar a realização de testes no sistema, utilize as credenciais da conta de administrador:

| Campo | Credencial |
|---|---|
| **E-mail** | `admin@chamados.com` |
| **Senha** | `Admin@123` |
| **Perfil** | Administrador |

### Links de acesso (localhost)

| Página | URL |
|---|---|
| **Login** | https://sistema-chamados-manutencao.onrender.com/usuarios/login |
| **Visualização e cadastro dos usuários** | https://sistema-chamados-manutencao.onrender.com/usuarios |

---

# Integrantes

| Integrante | Matrícula | Responsabilidade |
|---|---|---|
| Eduardo Nogueira Belarmino | GES 597 | Usuários e autenticação |
| Pedro Henrique dos Santos Araujo | GES 502 | Abertura e consulta de chamados |
| Felipe da Silva Inácio | GES 181 | Atendimento técnico e fluxo de status |
| Cauã de Souza Flauzino | GES 410 | Locais e equipamentos |
| Allan Pinto Nogueira | GES 671 | Histórico e filtros |
| Pedro Cesar Maia | GEC 1828 | Interface web e integração |

---

# Links do Projeto

## GitHub

https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado

## Jira

https://ppedrocesarmaiaa.atlassian.net/jira/software/projects/SCM/boards/1/backlog
