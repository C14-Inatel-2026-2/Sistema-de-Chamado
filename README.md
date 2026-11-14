# Sistema de Chamados de Manutenção

Projeto desenvolvido para a disciplina **C14 – Engenharia de Software**, do Instituto Nacional de Telecomunicações – INATEL.

## Descrição

O **Sistema de Chamados de Manutenção** é uma aplicação web desenvolvida para centralizar o registro, acompanhamento e gerenciamento de solicitações de manutenção.

O sistema permite cadastrar usuários com diferentes perfis, registrar locais e equipamentos, abrir chamados de manutenção, acompanhar o atendimento técnico, controlar a evolução dos status e consultar o histórico das alterações realizadas.

A proposta é facilitar a comunicação entre solicitantes, técnicos e responsáveis pelo sistema, permitindo maior organização e rastreabilidade dos serviços de manutenção.

A aplicação também possui uma interface web integrada ao backend, permitindo visualizar indicadores, consultar registros e executar operações diretamente pelo dashboard.

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
- JavaScript
- Maven
- PostgreSQL
- H2 Database
- JUnit 5
- Mockito
- JaCoCo
- Jenkins
- Jenkins Coverage Plugin
- Docker
- Render
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

## US06 – Dashboard e Interface Web

**Responsável:** Pedro Cesar Maia

**Como usuário do sistema,**  
quero acessar uma interface web integrada às funcionalidades existentes,  
para visualizar informações e executar as principais operações do Sistema de Chamados de Manutenção.

### Principais critérios

- Ao acessar `/dashboard`, o sistema deve apresentar a página principal.
- Ao acessar `/`, o usuário também deve visualizar o dashboard.
- O dashboard deve apresentar o título `Sistema de Chamados de Manutenção`.
- O dashboard deve apresentar a quantidade total de chamados.
- O dashboard deve apresentar a quantidade de chamados por status.
- O dashboard deve apresentar a quantidade de chamados por prioridade.
- O dashboard deve exibir os chamados mais recentes.
- A interface deve permitir consultar chamados.
- A interface deve permitir consultar locais.
- A interface deve permitir consultar equipamentos.
- A interface deve permitir abrir novos chamados.
- A interface deve permitir pesquisar chamados pelo ID.
- Caso o chamado não exista, a interface deve apresentar a mensagem retornada pelo backend.
- A interface deve permitir cadastrar locais.
- A interface deve permitir cadastrar equipamentos.
- Os locais cadastrados devem ser carregados dinamicamente nos campos de seleção.
- A interface deve utilizar os endpoints já existentes do backend, sem duplicar regras de negócio.

### Rotas relacionadas

```text
/
/dashboard
```

### Endpoints utilizados pela interface

```text
GET  /chamados
POST /chamados
GET  /chamados/{id}

GET  /locais
POST /locais

GET  /equipamentos
POST /equipamentos
```

**Issue Jira:** SCM-7  
**Status final:** Entregue

### Implementação

- `PaginaController.java`
- `dashboard.html`

### Testes

- `PaginaControllerTest.java`

### Pull Requests relacionados

- [#38](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/38) – implementação inicial da interface, testes e refactorings;
- [#39](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/39) – integração do Jenkins com deploy externo;
- [#40](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/40) – correção da publicação da cobertura JaCoCo;
- [#42](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/42) – operações finais do dashboard e integração com os endpoints do backend.

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

**RF36** – O dashboard deve apresentar indicadores de chamados por status e prioridade.

**RF37** – O dashboard deve apresentar os chamados cadastrados mais recentemente.

**RF38** – A interface deve permitir consultar chamados, locais e equipamentos.

**RF39** – A interface deve permitir abrir chamados.

**RF40** – A interface deve permitir pesquisar um chamado pelo identificador.

**RF41** – A interface deve informar quando o chamado pesquisado não existir.

**RF42** – A interface deve permitir cadastrar locais.

**RF43** – A interface deve permitir cadastrar equipamentos.

**RF44** – Os locais cadastrados devem ser carregados dinamicamente nos campos de seleção da interface.

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

**RNF14** – O processo de deploy deve publicar a aplicação fora da máquina executora do Jenkins.

---

# Testes

Os testes automatizados do projeto podem ser executados com:

```bash
mvn clean test
```

Para realizar a validação completa do projeto:

```bash
mvn clean verify
```

A última execução local validada apresentou:

```text
Tests run: 101
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

O projeto possui atualmente **101 testes automatizados**.

O relatório de cobertura do JaCoCo é gerado em:

```text
target/site/jacoco/
```

O arquivo XML utilizado pelo Jenkins fica em:

```text
target/site/jacoco/jacoco.xml
```

Os resultados individuais dos testes são armazenados em:

```text
target/surefire-reports/
```

---

# Testes da Interface Web

O `PaginaControllerTest` possui atualmente **9 testes**.

Os cenários incluem:

- acesso à rota `/` utilizando Mockito;
- acesso à rota `/dashboard` utilizando Mockito;
- acesso à rota `/` utilizando `ExtendedModelMap`;
- acesso à rota `/dashboard` utilizando `ExtendedModelMap`;
- contadores de status zerados quando não existem chamados;
- contadores de prioridade zerados quando não existem chamados;
- verificação do acesso ao `ChamadoRepository`;
- comportamento de `/dashboard` quando o `Model` é nulo;
- comportamento de `/` quando o `Model` é nulo.

Também foram realizados testes manuais das operações da interface.

Foram validados:

- abertura de chamado;
- cadastro de local;
- cadastro de equipamento;
- consulta de chamados;
- consulta de locais;
- consulta de equipamentos;
- atualização dos indicadores do dashboard;
- pesquisa de chamado por ID existente;
- pesquisa de chamado por ID inexistente;
- tratamento visual das mensagens de erro.

Um dos cenários negativos testados foi:

```text
GET /chamados/9999
```

Resposta do backend:

```text
Chamado não encontrado com id: 9999
```

A interface apresenta a mensagem retornada pelo backend ao usuário.

---

# Docker e Deploy Externo

O projeto está preparado para publicação externa utilizando Docker.

Foi criado um arquivo `Dockerfile` na raiz do projeto para realizar o build da aplicação utilizando Maven e Java 21 e executar o arquivo JAR gerado pelo Spring Boot.

No arquivo `application.properties` foi utilizada a configuração:

```properties
server.port=${PORT:8080}
```

Essa configuração permite utilizar a porta `8080` durante a execução local e aceitar a porta fornecida pelo ambiente externo.

A plataforma utilizada para o deploy é o **Render**.

### Serviço

```text
sistema-chamados-manutencao
```

### URL pública

https://sistema-chamados-manutencao.onrender.com

---

# CI/CD

O projeto utiliza **Jenkins** para automatizar o processo de integração e entrega contínua.

O pipeline é definido através do arquivo:

```text
Jenkinsfile
```

O fluxo configurado contempla:

```text
Validate
   ↓
Build
   ↓
Tests and Coverage
   ↓
Package
   ↓
Deploy Externo
   ↓
Post Actions
```

Durante a execução são realizados:

- validação do projeto;
- compilação;
- execução dos testes;
- geração do relatório JaCoCo;
- empacotamento do JAR;
- solicitação de deploy externo;
- publicação dos resultados dos testes;
- publicação da cobertura no Jenkins.

Os resultados do Surefire são publicados através de:

```groovy
junit 'target/surefire-reports/*.xml'
```

A cobertura JaCoCo é publicada através do Jenkins Coverage Plugin:

```groovy
recordCoverage(
    tools: [[
        parser: 'JACOCO',
        pattern: 'target/site/jacoco/jacoco.xml'
    ]],
    id: 'jacoco',
    name: 'JaCoCo Coverage'
)
```

---

# Integração Jenkins e Render

O Jenkins utiliza um **Deploy Hook** fornecido pelo Render para solicitar novos deploys.

A URL do hook é armazenada de forma segura nas credenciais do Jenkins.

### Credential ID

```text
render-deploy-hook
```

O valor real da credencial não é armazenado no repositório.

O fluxo utilizado é:

```text
GitHub
   ↓
Jenkins
   ↓
Build
   ↓
Testes
   ↓
JaCoCo
   ↓
Package
   ↓
Deploy Hook
   ↓
Render
```

---

# Organização do Projeto

A aplicação segue uma estrutura baseada em camadas:

```text
src/
├── main/
│   ├── java/
│   │   └── br/inatel/chamados/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── model/
│   │       ├── repository/
│   │       └── service/
│   │
│   └── resources/
│       ├── templates/
│       │   └── dashboard.html
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

ou:

```text
http://localhost:8080/dashboard
```

---

# Refactorings

Os refactorings registrados abaixo foram aplicados para melhorar a estrutura interna do código sem alterar seu comportamento externo.

## Cauã de Souza Flauzino

| # | Smell | Refactoring aplicado | Por quê | PR |
|---|---|---|---|---|
| 1 | Field Injection (`@Autowired` em campo) em `LocalController` e `EquipamentoController` | Replace Field Injection with Constructor Injection | As dependências ficam explícitas e imutáveis, e o controller pode ser criado em testes sem o Spring | [#30](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/30) |
| 2 | Controller acessando o repository direto, sem camada de service | Extract Class (`LocalService` e `EquipamentoService`) | Separa a camada web do acesso a dados, segue o padrão do projeto e permite testes unitários com Mockito | [#31](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/31) |

---

## Eduardo Nogueira

| # | Smell | Refactoring aplicado | Por quê | PR |
|---|---|---|---|---|
| 1 | Mensagem de erro de "email já cadastrado" escrita diretamente na exception em `UsuarioService` | Create Constant String (`EMAIL_JA_CADASTRADO`) | Centraliza a mensagem em uma constante, facilitando a manutenção e evitando strings repetidas no código | [#28](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/28/changes/32f81f5ae4de036c2b8973b70fc38b1c6ee016f6) |
| 2 | Parâmetro `Model` não utilizado no método `listar()` de `UsuarioController` | Remove Parameter | Remove um parâmetro desnecessário, simplificando a assinatura do método | [#28](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/28/changes/0e935c72b53a337361adb77bfe34cd3626acb1ba) |

---

## Pedro Henrique dos Santos Araujo

| # | Smell | Refactoring aplicado | Por quê | PR |
|---|---|---|---|---|
| 1 | Uso de `Optional` com `isEmpty()` seguido de `get()` | Replace with `orElseThrow` | Remove o `get()` inseguro e torna o código mais legível | [#27](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/27) |
| 2 | Setup repetido nos testes | Extract setup para `@BeforeEach` | Evita duplicação e facilita a criação de novos testes | [#29](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/29) |

---

## Allan Pinto Nogueira

### Refactoring 1

- **Code smell:** duplicação da validação de título e descrição em `ChamadoService.abrirChamado`.
- **Refactoring aplicado:** Remove Duplicate Code com Extract Method.
- **Método criado:** `validarTextoObrigatorio`.
- **Pull Request:** https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/32

### Refactoring 2

- **Code smell:** expressão condicional com comparações repetidas em `ChamadoService.transicaoPermitida`.
- **Refactoring aplicado:** Simplify Conditional Expression utilizando `switch`.
- **Pull Request:** https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/32

### Validação

| Estado | Testes | Falhas | Erros | Ignorados | Resultado |
|---|---:|---:|---:|---:|---|
| Antes dos refactorings | 30 | 0 | 0 | 0 | BUILD SUCCESS |
| Após o refactoring 1 | 40 | 0 | 0 | 0 | BUILD SUCCESS |
| Após o refactoring 2 | 65 | 0 | 0 | 0 | BUILD SUCCESS |

---

## Pedro Cesar Maia

### Refactoring 1 – Remoção de duplicação nas rotas do dashboard

**Code smell:** código duplicado.

Os métodos das rotas `/` e `/dashboard` executavam comportamento semelhante.

**Refactoring aplicado:** Remove Duplicate Code.

O método `inicio()` passou a reutilizar `dashboard()`:

```java
@GetMapping("/")
public String inicio(Model model) {
    return dashboard(model);
}
```

**Motivo:** reduzir duplicação e centralizar a preparação do dashboard.

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

**Motivo:** deixar clara a responsabilidade do método e melhorar a legibilidade.

**Pull Request:** [#38](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/38)

### Validação dos refactorings

| Estado | Testes | Falhas | Erros | Ignorados | Resultado |
|---|---:|---:|---:|---:|---|
| Antes das alterações finais | 87 | 0 | 0 | 0 | BUILD SUCCESS |
| Após os refactorings e novos testes | 98 | 0 | 0 | 0 | BUILD SUCCESS |
| Após integração do dashboard | 101 | 0 | 0 | 0 | BUILD SUCCESS |

---

# Evolução da Interface Web

Após a implementação inicial da US06, o dashboard foi ampliado para apresentar informações reais da aplicação.

Foram adicionados:

- integração com `ChamadoRepository`;
- total de chamados;
- chamados por status;
- chamados por prioridade;
- cinco chamados mais recentes;
- consulta de chamados;
- consulta de locais;
- consulta de equipamentos;
- abertura de chamados;
- pesquisa de chamado por ID;
- tratamento para chamado inexistente;
- cadastro de locais;
- cadastro de equipamentos;
- carregamento dinâmico de locais;
- mensagens visuais de sucesso e erro;
- integração com os endpoints através de JavaScript `fetch`.

### PR #42 – Operações do Dashboard

O Pull Request [#42](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/42) adicionou as operações finais da interface.

Entre as principais alterações estão:

- abertura de chamados através de `POST /chamados`;
- busca por ID utilizando `GET /chamados/{id}`;
- tratamento das respostas `404`;
- cadastro de locais através de `POST /locais`;
- cadastro de equipamentos através de `POST /equipamentos`;
- carregamento dos locais nos campos `select`;
- consulta dos registros existentes;
- exibição de mensagens de sucesso e erro.

---

# Uso de IA

Cada integrante declara abaixo como utilizou ferramentas de Inteligência Artificial durante o desenvolvimento.

## Pedro Henrique dos Santos Araujo

**Modelo:** Claude Sonnet 5.5 (Anthropic).

**Para que foi usado:**

- orientação nas entregas;
- revisão de código;
- elaboração de testes;
- documentação;
- commits e Pull Requests;
- revisão de refactorings.

### Prompt 1 – Definir o conjunto de testes

> "Proponha 4 testes unitários apenas para os arquivos do meu módulo: 2 com mock e 2 sem mock, incluindo pelo menos 1 teste negativo, seguindo boas práticas de nomenclatura e estrutura (given/when/then)."

**Resultado:** aceito com ajustes.

### Prompt 2 – Validar refactoring

> "Com base nos critérios dos slides em anexo, avalie se cada um dos meus dois refactorings é adequado. Aponte qual deles pode ser considerado apenas formatação e sugira uma alternativa no mesmo arquivo."

**Resultado:** ajustado.

### Prompt 3 – Stage de build

> "Complemente o stage Build do Jenkinsfile existente, sem substituir os demais stages: limpar o target antes de compilar e falhar o stage se o JAR esperado não for gerado. O agente é Windows, então use bat."

**Resultado:** aceito.

### Prompt 4 – Tratamento de erro

> "O POST /chamados com um local inexistente retorna 500 com stack trace. Proponha um tratamento no controller que devolva 400 com uma mensagem curta, e o teste unitário correspondente."

**Resultado:** ajustado após testes.

### Dinâmica de uso

As sugestões foram revisadas e executadas localmente antes da integração.

### O que foi feito sem IA

- Git;
- branches;
- commits;
- push;
- Pull Requests;
- execução dos testes;
- testes manuais;
- decisão dos refactorings;
- revisão final.

---

## Pedro Cesar Maia

**Modelo:** ChatGPT — GPT-5.6 Sol (OpenAI).

**Para que foi utilizado:**

- implementação e revisão da interface web;
- revisão do `PaginaController`;
- criação e revisão dos testes;
- análise dos requisitos da NP2;
- identificação e documentação de refactorings;
- integração frontend/backend;
- desenvolvimento do dashboard;
- tratamento de respostas HTTP;
- Jenkins;
- JaCoCo;
- Docker;
- Render;
- Git e Pull Requests;
- revisão do README.

### Prompt 1 – Revisão técnica da entrega NP2

**Contexto:** revisar o estado atual do projeto em relação aos critérios técnicos da NP2.

**Prompt:**

> “Analise o estado atual do projeto em relação aos requisitos da NP2. Verifique se a minha parte possui cobertura adequada de testes unitários, integração entre User Story, código e Pull Request, documentação dos refactorings, registro do uso de IA, integração contínua com Jenkins, cobertura JaCoCo e deploy externo. Aponte quais itens ainda precisam ser implementados, testados ou documentados antes da entrega.”

**Resultado:** aceito com ajustes.

A análise ajudou a identificar a necessidade de ampliar testes, documentar refactorings, registrar o uso de IA e validar CI/CD e deploy.

### Prompt 2 – Identificação de refactorings no PaginaController

**Contexto:** aplicar dois refactorings sem alterar o comportamento das rotas.

**Prompt:**

> “Analise o `PaginaController` e identifique dois code smells que possam ser corrigidos utilizando refactorings reconhecidos, sem alterar o comportamento das rotas `/` e `/dashboard`. Para cada caso, informe o smell, o refactoring aplicado, a justificativa técnica e quais testes devem continuar passando para comprovar que o comportamento foi preservado.”

**Resultado:** aceito com ajustes.

Foram utilizados:

- Remove Duplicate Code;
- Rename Method.

### Prompt 3 – Ampliação dos testes do PaginaController

**Contexto:** aumentar a cobertura da interface.

**Prompt:**

> “Atualize o `PaginaControllerTest` para aumentar a cobertura da interface web. Inclua testes para as rotas `/` e `/dashboard`, utilização de `Model` mockado com Mockito e `ExtendedModelMap`, validação dos atributos adicionados ao model, contagem de chamados por status e prioridade, verificação da chamada ao `ChamadoRepository` e testes negativos para `Model` nulo. Mantenha os testes unitários independentes do contexto completo do Spring.”

**Resultado:** aceito com ajustes.

O `PaginaControllerTest` passou a possuir **9 testes**.

### Prompt 4 – Restrição das alterações ao módulo da interface

**Contexto:** evoluir a US06 sem modificar as regras de negócio dos outros integrantes.

**Prompt:**

> “Revise a implementação da minha responsabilidade, limitada à interface web e integração. Faça alterações apenas em arquivos relacionados à US06, como `PaginaController`, `PaginaControllerTest` e `dashboard.html`. Não modifique controllers, services, repositories ou regras de negócio implementadas pelos outros integrantes. Caso a interface precise utilizar funcionalidades existentes, consuma os endpoints já disponíveis em vez de duplicar lógica no frontend ou alterar o backend.”

**Resultado:** aceito.

As alterações foram concentradas na interface, testes e integração.

### Prompt 5 – Integração técnica do dashboard com o backend

**Contexto:** integrar a interface Thymeleaf aos endpoints REST já existentes.

**Prompt:**

> “Atualize o `dashboard.html` utilizando JavaScript com `fetch` para integrar a interface aos endpoints existentes `GET /chamados`, `GET /chamados/{id}`, `POST /chamados`, `GET /locais`, `POST /locais`, `GET /equipamentos` e `POST /equipamentos`. Mantenha o backend atual sem modificar os controllers dos outros integrantes. Implemente formulários para abertura de chamado, cadastro de local e equipamento, carregamento dinâmico dos locais nos campos `select`, consulta de chamado por ID e tratamento das respostas HTTP de sucesso e erro, incluindo `404`. Preserve também os indicadores e as listagens já existentes no dashboard.”

**Resultado:** aceito com ajustes.

A interface passou a consumir os endpoints REST através de `fetch`, incluindo tratamento de erros HTTP e carregamento dinâmico dos dados.

### Validação das respostas da IA

As sugestões não foram incorporadas automaticamente.

O processo utilizado foi:

```text
Sugestão da IA
      ↓
Revisão
      ↓
Implementação
      ↓
Teste manual
      ↓
mvn clean test
      ↓
Commit
      ↓
Pull Request
```

### O que foi feito sem IA

- execução dos comandos Git;
- gerenciamento das branches;
- commits;
- push;
- criação dos Pull Requests;
- execução dos testes;
- testes manuais da interface;
- configuração das credenciais do Jenkins;
- execução dos builds;
- acompanhamento do Render;
- validação final das alterações.

### Compreensão

Todo código, teste, refactoring e documentação produzidos com apoio de IA foram revisados antes da utilização.

---

## Eduardo Nogueira

**Modelo:** ChatGPT — GPT-6 (OpenAI).

**Para que foi utilizado:**

- implementação do cadastro e autenticação;
- revisão do `UsuarioController` e `UsuarioService`;
- criptografia de senhas com BCrypt;
- correção de problemas de login e redirecionamento;
- refactorings;
- interpretação de testes;
- documentação;
- commits e Pull Requests;
- orientação com Git.

### Prompt 1 – Implementação da autenticação de usuários

> "Como implementar a autenticação de usuários utilizando e-mail e senha no UsuarioService e UsuarioController, incluindo a validação das credenciais e o gerenciamento da sessão?"

**Resultado:** aceito com ajustes.

### Prompt 2 – Redirecionamento após login

> "Ao fazer login, aparece uma Whitelabel Error Page com status 404 e a mensagem No static resource dashboard. Como corrigir o redirecionamento para a página correta?"

**Resultado:** aceito com ajustes.

### Prompt 3 – Refactorings

> "Analise o código e sugira dois refactorings simples que melhorem a legibilidade e a organização, sem modificar o comportamento esperado."

**Resultado:** aceito.

Foram aplicados:

- extração da constante `EMAIL_JA_CADASTRADO`;
- remoção do parâmetro `Model` não utilizado.

### Dinâmica de uso

A IA foi utilizada como ferramenta de consulta e apoio durante o desenvolvimento da parte de usuários e autenticação.

As sugestões foram analisadas e adaptadas conforme a estrutura do projeto.

### O que foi feito sem IA

- comandos Git;
- gerenciamento da branch `EduardoNogueira`;
- commits;
- push;
- testes Maven;
- revisão das alterações;
- Pull Requests;
- decisão final das alterações.

### Compreensão

As sugestões foram analisadas quanto à compatibilidade com a arquitetura antes da utilização.

---

# Acesso para Testes

Para facilitar os testes no sistema, pode ser utilizada a conta de administrador abaixo.

Somente administradores podem cadastrar usuários.

| Campo | Credencial |
|---|---|
| **E-mail** | `admin@chamados.com` |
| **Senha** | `Admin@123` |
| **Perfil** | Administrador |

### Links de acesso

| Página | URL |
|---|---|
| **Dashboard** | https://sistema-chamados-manutencao.onrender.com/ |
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

## Aplicação Publicada

https://sistema-chamados-manutencao.onrender.com

---

# Situação Atual

A última versão validada localmente apresentou:

```text
Tests run: 101
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

O JaCoCo analisou **24 classes**.

A interface web foi validada com:

- abertura de chamado;
- cadastro de local;
- cadastro de equipamento;
- consulta de chamados;
- consulta de locais;
- consulta de equipamentos;
- busca por ID existente;
- busca por ID inexistente;
- tratamento de erros;
- atualização dos indicadores do dashboard.

As operações finais da interface foram integradas através do **PR #42**.

A infraestrutura de CI/CD utiliza Jenkins, JaCoCo, Docker e Render para testes, cobertura, empacotamento e publicação externa.