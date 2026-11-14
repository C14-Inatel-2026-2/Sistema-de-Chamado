# Sistema de Chamados de Manutenção

Projeto desenvolvido para a disciplina **C14 – Engenharia de Software**, do Instituto Nacional de Telecomunicações – INATEL.

---

## Descrição

O **Sistema de Chamados de Manutenção** é uma aplicação web desenvolvida para centralizar o registro, acompanhamento e gerenciamento de solicitações de manutenção.

O sistema permite cadastrar usuários com diferentes perfis, registrar locais e equipamentos, abrir e consultar chamados, realizar o atendimento técnico, controlar a evolução dos status e registrar o histórico das alterações.

O projeto foi desenvolvido utilizando práticas de Engenharia de Software, incluindo testes automatizados, versionamento com Git, revisão por Pull Requests, integração contínua, metodologia ágil, refactoring e documentação do uso de Inteligência Artificial.

---

## Objetivo

Gerenciar o ciclo de vida de um chamado de manutenção, desde sua abertura até seu encerramento, mantendo informações relacionadas a:

- usuários;
- solicitantes;
- técnicos;
- chamados;
- prioridades;
- locais;
- equipamentos;
- status;
- histórico de alterações.

---

# Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- Maven
- H2 Database
- PostgreSQL
- JUnit 5
- Mockito
- JaCoCo
- Jenkins
- Git
- GitHub
- Jira

---

# Funcionalidades

Entre as principais funcionalidades do sistema estão:

- cadastro de usuários;
- definição de perfis de acesso;
- abertura de chamados;
- consulta de chamados;
- atribuição de técnico;
- atualização do status de atendimento;
- cadastro de locais;
- cadastro de equipamentos;
- registro de histórico;
- filtros de chamados;
- dashboard web;
- validações de regras de negócio.

---

# Instalação e Execução

## Pré-requisitos

Para executar o projeto localmente é necessário possuir:

- Java 21;
- Maven;
- Git.

Clone o repositório:

```bash
git clone https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado.git
```

Entre na pasta do projeto:

```bash
cd Sistema-de-Chamado
```

Execute os testes:

```bash
mvn clean test
```

Para executar também as verificações e relatórios:

```bash
mvn clean verify
```

Execute a aplicação:

```bash
mvn spring-boot:run
```

Após a inicialização, a aplicação estará disponível em:

```text
http://localhost:8080
```

---

# Uso

## Dashboard

O dashboard pode ser acessado por:

```text
/
```

ou:

```text
/dashboard
```

## Chamados

Principais endpoints relacionados aos chamados:

```text
POST /chamados
GET /chamados
GET /chamados/{id}
```

---

# Gestão Ágil

O grupo utilizou a metodologia **Scrum** para organizar o desenvolvimento.

O Jira foi utilizado para gerenciamento de:

- Product Backlog;
- User Stories;
- critérios de aceite;
- Sprint Backlog;
- responsáveis;
- acompanhamento das atividades.

## Papéis

**Product Owner:** [PREENCHER]

**Scrum Master / Facilitador:** [PREENCHER]

**Equipe de desenvolvimento:** todos os integrantes do grupo.

## Cadência

O desenvolvimento foi organizado em Sprints relacionadas às diferentes entregas da disciplina.

**Duração das Sprints:** [PREENCHER DE ACORDO COM O JIRA]

## Definition of Ready — DoR

Uma atividade era considerada preparada para desenvolvimento quando:

- possuía objetivo definido;
- possuía responsável;
- apresentava critérios de aceite;
- tinha escopo compreendido pelo integrante.

## Definition of Done — DoD

Uma atividade era considerada concluída quando:

- a implementação estava finalizada;
- os testes relacionados estavam executados;
- o código estava versionado;
- um Pull Request havia sido criado;
- outro integrante havia realizado a revisão;
- os critérios de aceite haviam sido verificados;
- o pipeline estava funcionando quando aplicável.

## Métricas

**Issues concluídas:** [PREENCHER]

**Issues pendentes ou parciais:** [PREENCHER]

**Pull Requests realizados:** [PREENCHER]

## Jira

https://ppedrocesarmaiaa.atlassian.net/jira/software/projects/SCM/boards/1/backlog

---

# Product Backlog

## US01 – Cadastro de Usuários

**Responsável:** Eduardo Nogueira Belarmino

**Como responsável pelo sistema,**  
quero cadastrar novos usuários informando seus dados pessoais e de acesso,  
para permitir que diferentes usuários tenham acesso ao sistema de chamados de manutenção de acordo com seus respectivos perfis.

### Critérios de aceite

- O sistema deve apresentar um formulário com nome, e-mail, senha e perfil.
- Todos os campos obrigatórios devem ser preenchidos.
- Usuários com dados válidos devem ser cadastrados.
- O sistema deve impedir cadastro com e-mail já utilizado.
- O sistema deve informar os erros de validação encontrados.

**Issue Jira:** SCM-8  
**Prioridade:** [PREENCHER]  
**Status final:** [PREENCHER: Entregue / Parcial / Descartada]  
**PR:** [PREENCHER]  
**Testes:** testes relacionados ao módulo de usuários.

---

## US02 – Abertura e Consulta de Chamados

**Responsável:** Pedro Henrique dos Santos Araujo

**Como solicitante,**  
quero abrir chamados de manutenção e consultá-los,  
para que a equipe técnica saiba qual problema precisa ser resolvido e eu possa acompanhar o andamento.

### Critérios de aceite

- título e descrição são obrigatórios;
- a prioridade deve ser informada;
- um novo chamado inicia com status `ABERTO`;
- a data e hora de abertura são registradas automaticamente;
- o sistema deve listar todos os chamados;
- deve ser possível buscar um chamado pelo identificador;
- um identificador inexistente deve gerar uma mensagem adequada;
- o chamado deve possuir os vínculos necessários.

**Issue Jira:** SCM-9  
**Prioridade:** [PREENCHER]  
**Status final:** [PREENCHER]  
**PR:** [PREENCHER]  
**Testes:** testes relacionados à abertura e consulta de chamados.

---

## US03 – Atendimento Técnico

**Responsável:** Felipe da Silva Inácio

**Como técnico de manutenção,**  
quero visualizar os chamados atribuídos a mim e atualizar seus status durante o atendimento,  
para acompanhar e organizar os serviços de manutenção sob minha responsabilidade.

### Critérios de aceite

- o técnico deve visualizar os chamados atribuídos;
- o sistema deve apresentar título, descrição e prioridade;
- o técnico deve conseguir iniciar o atendimento;
- `ABERTO` deve poder ser alterado para `EM_ANDAMENTO`;
- `EM_ANDAMENTO` deve poder ser alterado para `RESOLVIDO`;
- `RESOLVIDO` deve poder ser alterado para `FECHADO`;
- transições inválidas devem ser impedidas.

### Fluxo

```text
ABERTO
   ↓
EM_ANDAMENTO
   ↓
RESOLVIDO
   ↓
FECHADO
```

**Issue Jira:** SCM-10  
**Prioridade:** [PREENCHER]  
**Status final:** [PREENCHER]  
**PR:** [PREENCHER]  
**Testes:** testes relacionados ao atendimento e às transições de status.

---

## US04 – Cadastro de Locais e Equipamentos

**Responsável:** Cauã de Souza Flauzino

**Como administrador do sistema,**  
quero cadastrar os locais físicos e os equipamentos da instituição,  
para que os solicitantes possam indicar onde e em qual equipamento ocorreu o problema.

### Critérios de aceite

- permitir cadastro de locais;
- permitir listagem de locais;
- permitir cadastro de equipamentos;
- permitir listagem de equipamentos;
- permitir associação entre equipamento e local;
- validar os dados necessários para realização do cadastro.

**Issue Jira:** SCM-11  
**Prioridade:** [PREENCHER]  
**Status final:** [PREENCHER]  
**PR:** [PREENCHER]  
**Testes:** testes relacionados aos locais e equipamentos.

---

## US05 – Histórico e Filtros de Chamados

**Responsável:** Allan Pinto Nogueira

**Como usuário do sistema,**  
eu quero consultar o histórico e filtrar os chamados,  
para acompanhar as alterações realizadas e localizar chamados com mais facilidade.

### Critérios de aceite

**DADO** que um chamado tenha alterações registradas  
**QUANDO** o usuário consultar seu histórico  
**ENTÃO** o sistema deve exibir as alterações realizadas no chamado.

**DADO** que o status de um chamado seja alterado  
**QUANDO** a alteração for registrada no histórico  
**ENTÃO** o sistema deve armazenar o status anterior, o novo status, o usuário responsável e a data da alteração.

**DADO** que existam vários chamados cadastrados  
**QUANDO** o usuário utilizar um dos filtros disponíveis  
**ENTÃO** o sistema deve apresentar apenas os chamados correspondentes ao filtro selecionado.

**DADO** que nenhum chamado corresponda ao filtro informado  
**QUANDO** a busca for realizada  
**ENTÃO** o sistema deve retornar uma lista vazia ou informar que nenhum chamado foi encontrado.

**DADO** que um chamado possua alterações de status registradas  
**QUANDO** o usuário consultar o histórico  
**ENTÃO** o sistema deve apresentar o status anterior e o novo status.

**DADO** que existam registros no histórico  
**QUANDO** o usuário visualizar esses registros  
**ENTÃO** devem ser apresentadas a data e a hora das alterações em ordem cronológica.

**DADO** que um chamado não possua alterações registradas  
**QUANDO** o usuário consultar seu histórico  
**ENTÃO** o sistema deve informar que não existem alterações registradas.

**Issue Jira:** SCM-12  
**Prioridade:** [PREENCHER]  
**Status final:** [PREENCHER / CONFIRMAR COM ALLAN]  
**PR:** [PREENCHER]  
**Testes:** testes relacionados ao histórico e filtros.

---

## US06 – Dashboard Inicial

**Responsável:** Pedro Cesar Maia

**Como usuário do sistema,**  
quero acessar um dashboard inicial,  
para visualizar a página principal do Sistema de Chamados de Manutenção.

### Critérios de aceite

**DADO** que a aplicação esteja em execução  
**QUANDO** o usuário acessar `/dashboard`  
**ENTÃO** o sistema deve exibir a página principal.

**DADO** que o dashboard seja carregado  
**QUANDO** a página for exibida  
**ENTÃO** deve aparecer o título `Sistema de Chamados de Manutenção`.

**DADO** que a aplicação esteja em execução  
**QUANDO** o usuário acessar `/`  
**ENTÃO** deve visualizar o dashboard.

**Issue Jira:** SCM-7  
**Prioridade:** [PREENCHER]  
**Status final:** Entregue

### Implementação

```text
PaginaController.java
dashboard.html
```

### Testes

```text
PaginaControllerTest.java
```

**PR:** [PREENCHER COM O LINK DO SEU PR FINAL]

---

# Requisitos Funcionais

## Usuários

**RF01** – O sistema deve permitir cadastrar usuários.

**RF02** – O cadastro deve possuir nome, e-mail, senha e perfil.

**RF03** – O sistema deve validar os campos obrigatórios.

**RF04** – O sistema deve impedir cadastro com e-mail já utilizado.

## Chamados

**RF05** – O sistema deve permitir abertura de chamados.

**RF06** – O chamado deve possuir título, descrição e prioridade.

**RF07** – O sistema deve trabalhar com prioridades válidas.

**RF08** – Novos chamados devem iniciar com status `ABERTO`.

**RF09** – A data e hora da abertura devem ser registradas.

**RF10** – O sistema deve permitir listar chamados.

**RF11** – O sistema deve permitir consultar chamado pelo identificador.

**RF12** – O sistema deve informar quando um chamado não existir.

## Atendimento Técnico

**RF13** – O sistema deve permitir atribuir técnico ao chamado.

**RF14** – O técnico deve poder atualizar o status do chamado.

**RF15** – O sistema deve respeitar o fluxo:

```text
ABERTO → EM_ANDAMENTO → RESOLVIDO → FECHADO
```

**RF16** – O sistema deve impedir transições inválidas.

## Locais e Equipamentos

**RF17** – O sistema deve permitir cadastrar locais.

**RF18** – O sistema deve permitir listar locais.

**RF19** – O sistema deve permitir cadastrar equipamentos.

**RF20** – O sistema deve permitir listar equipamentos.

**RF21** – Equipamentos devem poder ser relacionados a locais.

## Histórico

**RF22** – O sistema deve registrar alterações dos chamados.

**RF23** – O histórico deve registrar status anterior e novo status.

**RF24** – O histórico deve registrar data e horário.

## Interface Web

**RF25** – O sistema deve possuir interface web.

**RF26** – O sistema deve disponibilizar um dashboard inicial.

**RF27** – O dashboard deve estar disponível através de `/` e `/dashboard`.

---

# Requisitos Não Funcionais

**RNF01** – Utilizar Java 21.

**RNF02** – Utilizar Maven para gerenciamento de dependências.

**RNF03** – Utilizar Spring Boot.

**RNF04** – Utilizar Spring Data JPA.

**RNF05** – Utilizar Thymeleaf para a interface web.

**RNF06** – Utilizar banco de dados relacional.

**RNF07** – Possuir testes automatizados utilizando JUnit 5.

**RNF08** – Utilizar Mockito quando necessário para simular dependências.

**RNF09** – Utilizar JaCoCo para análise de cobertura.

**RNF10** – Possuir pipeline CI/CD utilizando Jenkins.

**RNF11** – Utilizar Git e GitHub para versionamento.

**RNF12** – Utilizar branches e Pull Requests.

**RNF13** – Publicar a aplicação fora da máquina executora do Jenkins.

---

# Testes

Os testes podem ser executados através de:

```bash
mvn clean test
```

Para executar as verificações completas:

```bash
mvn clean verify
```

Relatório do JaCoCo:

```text
target/site/jacoco/index.html
```

Relatórios dos testes:

```text
target/surefire-reports/
```

Uma execução anterior da `main` registrou:

```text
87 testes
0 falhas
0 erros
```

Após todas as alterações finais:

```text
[PREENCHER COM O RESULTADO FINAL]
```

---

# CI/CD

O projeto utiliza **Jenkins** para integração e entrega contínua.

O pipeline contém etapas relacionadas a:

```text
Validate
   ↓
Build
   ↓
Tests and Coverage
   ↓
Package
   ↓
Deploy
```

O pipeline está definido no arquivo:

```text
Jenkinsfile
```

## Deploy

A entrega final prevê publicação da aplicação fora da máquina executora do Jenkins.

**Serviço utilizado:** [PREENCHER]

**URL pública:** [PREENCHER]

---

# Refactorings

Cada integrante realizou refactorings buscando melhorar a estrutura interna do código sem alterar seu comportamento externo.

## Pedro Cesar Maia

### Refactoring 1 – Remoção de código duplicado nas rotas do dashboard

**Code smell:** código duplicado.

Anteriormente, os métodos responsáveis pelas rotas `/` e `/dashboard` executavam a mesma preparação do `Model` e retornavam a mesma página.

**Refactoring aplicado:** Remove Duplicate Code.

O método `inicio()` passou a reutilizar o método `dashboard()`:

```java
@GetMapping("/")
public String inicio(Model model) {
    return dashboard(model);
}
```

**Motivo:** reduzir duplicação e deixar apenas um ponto responsável pela preparação do dashboard.

**PR:** [PREENCHER COM SEU PR]

---

### Refactoring 2 – Renomeação do método de preparação do Model

**Code smell:** nome pouco específico.

O método:

```text
prepararModel()
```

foi renomeado para:

```text
adicionarTituloAoModel()
```

**Refactoring aplicado:** Rename Method.

O código passou a utilizar:

```java
private void adicionarTituloAoModel(Model model) {
    if (model == null) {
        throw new IllegalArgumentException("O model não pode ser nulo");
    }

    model.addAttribute("titulo", TITULO);
}
```

**Motivo:** tornar o nome mais claro e representar corretamente a responsabilidade executada pelo método.

**PR:** [PREENCHER COM SEU PR]

### Validação

Os testes foram executados para verificar que os refactorings não modificaram o comportamento externo.

**Antes dos refactorings:**

```text
[PREENCHER RESULTADO DO TESTE]
```

**Depois dos refactorings:**

```text
[PREENCHER RESULTADO DO TESTE]
```

---

## Refactorings dos demais integrantes

As informações de code smell, refactoring aplicado, justificativa e Pull Request dos demais integrantes devem permanecer conforme registrado no README atual do grupo.

---

# Uso de Inteligência Artificial

O uso de Inteligência Artificial foi declarado de forma transparente pelos integrantes que utilizaram essas ferramentas durante o desenvolvimento.

---

## Pedro Cesar Maia

**Modelo:** ChatGPT — GPT-5.6 Sol (OpenAI).

### Para que foi utilizado

- apoio na implementação e revisão da interface web;
- revisão do `PaginaController`;
- criação e revisão dos testes unitários;
- análise dos requisitos das entregas;
- identificação de possíveis refactorings;
- revisão da documentação;
- auxílio na organização das User Stories no Jira;
- interpretação dos requisitos relacionados ao Jenkins e CI/CD;
- apoio na preparação da entrega final.

### Exemplos de prompts utilizados

Os textos abaixo representam versões organizadas das solicitações realizadas durante o desenvolvimento, preservando o objetivo técnico dos prompts utilizados.

#### Prompt 1 – Análise da NP2

**Contexto:** após as entregas anteriores, foi necessário comparar o estado do projeto com os requisitos da NP2.

**Prompt:**

> “Analise os requisitos da NP2 e compare com o estado atual do nosso Sistema de Chamados de Manutenção. Verifique se faltam testes, documentação, refactorings, requisitos de CI/CD ou outros itens importantes para a entrega final.”

**Resultado:** aceito com ajustes.

A análise ajudou a identificar itens relacionados aos refactorings individuais, rastreabilidade das histórias, documentação do uso de IA e requisitos do deploy.

---

#### Prompt 2 – Refactoring do PaginaController

**Contexto:** a entrega final exigia pelo menos dois refactorings por integrante sem alteração do comportamento externo.

**Prompt:**

> “Analise o PaginaController da minha parte do projeto e proponha dois refactorings simples que atendam aos critérios da disciplina. As alterações não devem modificar o comportamento das rotas `/` e `/dashboard` e precisam ser fáceis de justificar na defesa.”

**Resultado:** aceito.

Foram utilizados:

- remoção da duplicação entre as rotas;
- renomeação de `prepararModel()` para `adicionarTituloAoModel()`.

---

#### Prompt 3 – Revisão dos testes

**Contexto:** o `PaginaControllerTest` já possuía testes, mas foi verificada a possibilidade de melhorar a cobertura.

**Prompt:**

> “Revise os testes existentes do PaginaController e verifique se vale a pena adicionar novos casos. Quero aumentar a cobertura apenas com testes relevantes para a US06, incluindo `/`, `/dashboard`, Model real, Mockito e casos negativos.”

**Resultado:** aceito com ajustes.

Os testes passaram a cobrir:

- `/` utilizando mock;
- `/dashboard` utilizando mock;
- `/` utilizando `ExtendedModelMap`;
- `/dashboard` utilizando `ExtendedModelMap`;
- `/` com `Model` nulo;
- `/dashboard` com `Model` nulo.

---

#### Prompt 4 – Revisão somente da minha parte

**Contexto:** durante a preparação da entrega final, foi solicitado que nenhuma modificação fosse realizada nos módulos dos outros integrantes.

**Prompt:**

> “Considere esta versão atualizada do projeto e revise somente a minha parte. Verifique o PaginaController, PaginaControllerTest e a documentação relacionada à minha contribuição. Se necessário, melhore os testes e prepare a seção de Uso de IA, sem modificar os módulos dos outros integrantes.”

**Resultado:** aceito com ajustes.

As mudanças foram limitadas ao controller da interface, seus testes e documentação relacionada à contribuição de Pedro Cesar.

### Dinâmica de uso

A IA foi utilizada como ferramenta de apoio ao desenvolvimento.

Foram fornecidos requisitos, arquivos, trechos do projeto e dúvidas específicas. As respostas eram analisadas antes da incorporação das sugestões.

Quando uma sugestão ultrapassava o escopo da minha responsabilidade, era solicitado que a alteração fosse limitada somente à minha parte.

### O que foi feito sem IA

Foram realizados diretamente por mim:

- utilização do ambiente de desenvolvimento;
- execução de comandos Git;
- gerenciamento da minha branch;
- commits e pushes;
- criação e acompanhamento de Pull Requests;
- organização das atividades no Jira;
- execução local dos testes;
- conferência das alterações;
- decisão final sobre quais sugestões seriam utilizadas.

A execução real do Jenkins, merges, aprovações de Pull Requests e validações em ferramentas externas não foram realizadas pela IA.

### Compreensão

Todo código, teste, refactoring e documentação sugeridos com apoio de IA foram revisados antes de sua utilização.

A IA foi utilizada como ferramenta de suporte, mantendo a responsabilidade do integrante pela compreensão e validação das alterações realizadas.

---

## Pedro Henrique dos Santos Araujo

**Modelo:** Claude Sonnet 5.5 (Anthropic), usado em todas as sessões.

### Para que foi usado

- orientação passo a passo das entregas;
- escrita e revisão do módulo de chamados;
- testes unitários;
- mensagens de commit e PR;
- história SCM-9;
- revisão de refactorings.

### Exemplos

Preservar nesta seção a declaração completa enviada pelo próprio integrante.

---

## Demais integrantes

Cada integrante que utilizou IA deve declarar sua utilização real, incluindo modelo, finalidade, prompts utilizados e quais sugestões foram aceitas, ajustadas ou descartadas.

---

# Dinâmica de Desenvolvimento

O desenvolvimento foi dividido entre os integrantes de acordo com módulos e responsabilidades.

O fluxo principal utilizado foi:

```text
Issue / User Story
        ↓
Branch
        ↓
Implementação
        ↓
Testes
        ↓
Commit
        ↓
Push
        ↓
Pull Request
        ↓
Code Review
        ↓
Merge na main
```

## Divisão de tarefas

Cada integrante ficou responsável por uma área principal do sistema, com integração entre os módulos ao longo das entregas.

## Code Review

As alterações foram enviadas através de Pull Requests e revisadas por outros integrantes antes da integração na `main`.

## Bloqueios e dificuldades

[PREENCHER COM PROBLEMAS REAIS ENFRENTADOS PELO GRUPO]

Exemplos que podem ser utilizados apenas caso realmente tenham acontecido:

- conflitos entre branches;
- erros de integração entre entidades e services;
- erros de compilação após merges;
- configuração do Jenkins;
- dependências entre funcionalidades desenvolvidas por integrantes diferentes.

## Lições aprendidas

[PREENCHER COM AS LIÇÕES REAIS DO GRUPO]

---

# Organização do Projeto

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
│   └── resources/
│       ├── templates/
│       ├── static/
│       └── application.properties
│
└── test/
    └── java/
```

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

[PREENCHER COM A URL PÚBLICA APÓS O DEPLOY]