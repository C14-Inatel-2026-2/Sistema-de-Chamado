# Sistema de Chamados de Manutenção

Projeto desenvolvido para a disciplina **C14 - Engenharia de Software** do INATEL.

## Descrição

O Sistema de Chamados de Manutenção é uma aplicação web destinada ao registro, acompanhamento e gerenciamento de chamados relacionados a salas, laboratórios, locais e equipamentos.

A aplicação permite organizar o processo de manutenção desde a abertura de um chamado até seu atendimento, alteração de status e registro de histórico.

## Objetivo

Centralizar o gerenciamento de solicitações de manutenção, permitindo que usuários registrem problemas e que técnicos acompanhem e atualizem o andamento dos chamados.

## Principais funcionalidades

- Cadastro de usuários.
- Cadastro e consulta de locais.
- Cadastro e consulta de equipamentos.
- Abertura de chamados de manutenção.
- Consulta de chamados cadastrados.
- Atribuição de técnico a um chamado.
- Alteração do status de um chamado.
- Controle das transições de status.
- Registro do histórico de alterações do chamado.
- Dashboard inicial da aplicação.

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Maven
- Spring MVC
- Thymeleaf
- Spring Data JPA
- H2
- PostgreSQL
- JUnit 5
- Mockito
- JaCoCo
- Jenkins
- Git
- GitHub

## Método Ágil

O grupo utiliza **Scrum** para organizar o desenvolvimento do projeto.

O trabalho é organizado utilizando:

- Product Backlog
- User Stories
- Critérios de aceite
- Sprint
- Sprint Backlog
- Acompanhamento das tarefas no Jira

Cada integrante do grupo é responsável por pelo menos uma User Story relacionada à sua área de desenvolvimento.

## Product Backlog

### US01 - Cadastro de usuários

**Responsável:** Eduardo Nogueira Belarmino

> Como administrador, eu quero cadastrar usuários para permitir que novas pessoas utilizem o sistema.

### US02 - Abertura de chamados

**Responsável:** Pedro Henrique dos Santos Araujo

> Como solicitante, eu quero abrir um chamado de manutenção para informar um problema que precisa ser solucionado.

### US03 - Atendimento técnico

**Responsável:** Felipe da Silva Inácio

> Como técnico, eu quero assumir um chamado e alterar seu status para registrar o andamento do atendimento.

### US04 - Cadastro de locais e equipamentos

**Responsável:** Cauã de Souza Flauzino

> Como administrador, eu quero cadastrar locais e equipamentos para identificar onde os problemas de manutenção estão ocorrendo.

### US05 - Histórico de chamados

**Responsável:** Allan Pinto Nogueira

> Como usuário, eu quero consultar o histórico de alterações de um chamado para acompanhar sua evolução.

### US06 - Dashboard inicial

**Responsável:** Pedro Cesar Maia

> Como usuário do sistema, eu quero acessar um dashboard inicial para visualizar a página principal do Sistema de Chamados de Manutenção.

## Requisitos Funcionais

- **RF01** - O sistema deve permitir o cadastro de usuários.
- **RF02** - O sistema deve permitir cadastrar locais.
- **RF03** - O sistema deve permitir cadastrar equipamentos.
- **RF04** - O sistema deve permitir abrir chamados de manutenção.
- **RF05** - Todo novo chamado deve iniciar com o status `ABERTO`.
- **RF06** - O sistema deve permitir consultar todos os chamados cadastrados.
- **RF07** - O sistema deve permitir consultar um chamado pelo seu identificador.
- **RF08** - O sistema deve permitir atribuir um usuário com perfil de técnico a um chamado.
- **RF09** - O sistema deve permitir atualizar o status dos chamados.
- **RF10** - O sistema deve aceitar somente as transições de status previstas no fluxo da aplicação.
- **RF11** - O sistema deve permitir registrar o histórico de alterações de status de um chamado.
- **RF12** - O sistema deve disponibilizar um dashboard inicial acessível pelas rotas `/` e `/dashboard`.

## Requisitos Não Funcionais

- **RNF01** - O projeto deve utilizar Java 21.
- **RNF02** - O gerenciamento de dependências e o processo de build devem ser realizados com Maven.
- **RNF03** - A aplicação deve utilizar Spring Boot como framework principal.
- **RNF04** - A persistência dos dados deve utilizar Spring Data JPA.
- **RNF05** - A interface web deve utilizar Thymeleaf e ser acessível por navegador.
- **RNF06** - O projeto deve possuir testes automatizados executados com JUnit 5 e Mockito.
- **RNF07** - O projeto deve gerar relatório de cobertura de testes utilizando JaCoCo.
- **RNF08** - O processo de integração e entrega deve possuir pipeline de CI/CD utilizando Jenkins.
- **RNF09** - O projeto deve ser versionado utilizando Git e armazenado no GitHub.
- **RNF10** - Alterações relevantes devem ser realizadas em branches e integradas ao código principal por meio de Pull Requests.

## Fluxo dos chamados

O fluxo previsto para os chamados é:

```text
ABERTO
   ↓
EM_ANDAMENTO
   ↓
RESOLVIDO
   ↓
FECHADO
```

Transições fora desse fluxo são consideradas inválidas.

## Estrutura principal do projeto

```text
src/
├── main/
│   ├── java/br/inatel/chamados/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/
│   └── resources/
│       ├── templates/
│       ├── static/
│       └── application.properties
└── test/
    └── java/br/inatel/chamados/
        ├── controller/
        ├── model/
        └── service/
```

## Executar a aplicação

Na raiz do projeto:

```bash
mvn spring-boot:run
```

Depois acesse:

```text
http://localhost:8080/
```

## Executar os testes

Para executar todos os testes:

```bash
mvn test
```

## Cobertura de testes

O projeto utiliza JaCoCo para geração de relatório de cobertura.

Após a execução configurada pelo Maven/Jenkins, o relatório pode ser encontrado em:

```text
target/site/jacoco/
```

## CI/CD

A pipeline do projeto é configurada por meio do arquivo:

```text
Jenkinsfile
```

A pipeline contempla etapas relacionadas a:

- Validação
- Build
- Testes e cobertura
- Empacotamento
- Deploy

## Gestão Ágil

O projeto deve ser acompanhado no Jira utilizando Scrum, com as User Stories organizadas no Product Backlog e em Sprint.

**Link do Jira:** (https://ppedrocesarmaiaa.atlassian.net/jira/software/projects/SCM/boards/1/backlog)


## Integrantes

- Eduardo Nogueira Belarmino - GES 597
- Pedro Henrique dos Santos Araujo - GES 502
- Felipe da Silva Inácio - GES 181
- Cauã de Souza Flauzino - GES 410
- Allan Pinto Nogueira - GES 671
- Pedro Cesar Maia - GEC 1828 - Líder
