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
- O chamado deve ficar vinculado ao solicitante e ao local/equipamento relacionado ao problema.

### Endpoints relacionados

```text
POST /chamados
GET /chamados
GET /chamados/{id}
```

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
- O número de patrimônio é obrigatório.
- O número de patrimônio deve ser único.
- Todo equipamento deve estar associado a um local existente.
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

**RF24** – O equipamento deve possuir nome ou descrição e número de patrimônio.

**RF25** – O número de patrimônio deve ser único.

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

---

# Testes

Os testes automatizados do projeto podem ser executados com:

```bash
mvn test
```

O relatório de cobertura do JaCoCo pode ser gerado durante a execução configurada no projeto.

Quando disponível, o relatório pode ser encontrado em:

```text
target/site/jacoco/
```

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

# Refactorings

Cada integrante aplicou pelo menos 2 refactorings, com testes e pipeline verdes antes e depois de cada um.

## Cauã de Souza Flauzino

| # | Smell | Refactoring aplicado | Por quê | PR |
|---|-------|----------------------|---------|----|
| 1 | Field Injection (`@Autowired` em campo) em `LocalController` e `EquipamentoController` | Replace Field Injection with Constructor Injection | As dependências ficam explícitas e imutáveis, e o controller pode ser criado em testes sem o Spring | [#30](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/30) |
| 2 | Controller acessando o repository direto, sem camada de service | Extract Class (`LocalService` e `EquipamentoService`) | Separa a camada web do acesso a dados, segue o padrão do projeto e permite testes unitários com Mockito | [#31](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/31) |

## Eduardo Nogueira

| # | Smell | Refactoring aplicado | Por quê | PR |
|---|-------|----------------------|---------|----|
| 1 | Mensagem de erro de "email já cadastrado" escrita diretamente na exception em `UsuarioService` | Create Constant String (`EMAIL_JA_CADASTRADO`) | Centraliza a mensagem em uma constante, facilitando a manutenção e evitando strings repetidas no código | [#28](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/28/changes/32f81f5ae4de036c2b8973b70fc38b1c6ee016f6) |
| 2 | Parâmetro `Model` não utilizado no método `listar()` de `UsuarioController` | Remove Parameter | Remove um parâmetro desnecessário, simplificando a assinatura do método e evitando dependências que não são utilizadas | [#28](https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/28/changes/0e935c72b53a337361adb77bfe34cd3626acb1ba) |


## Allan

**Refactoring 1**

- Code smell: duplicação da validação de título e descrição em `ChamadoService.abrirChamado`, com a mesma verificação de texto nulo ou em branco.
- Refactoring aplicado: Remove Duplicate Code com Extract Method, criando o método privado `validarTextoObrigatorio`.
- Justificativa: centralizar a validação repetida, preservando a ordem das verificações, as mensagens de erro e os dados do chamado.
- Pull Request: https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/32

**Refactoring 2**

- Code smell: expressão condicional com comparações repetidas e operadores `&&` e `||` em `ChamadoService.transicaoPermitida`.
- Refactoring aplicado: Simplify Conditional Expression, substituindo a expressão por um `switch` sobre o status atual.
- Justificativa: explicitar o destino permitido para cada status, mantendo somente as transições ABERTO → EM_ANDAMENTO → RESOLVIDO → FECHADO e a rejeição de status atual nulo.
- Pull Request: https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado/pull/32

Validação dos refactorings antes da integração da `main`, em 13/11/2026, com Java 21 e `mvn clean test`:

| Estado | Testes | Falhas | Erros | Ignorados | Resultado |
| --- | --- | --- | --- | --- | --- |
| Antes dos refactorings | 30 | 0 | 0 | 0 | BUILD SUCCESS |
| Após o refactoring 1 | 40 | 0 | 0 | 0 | BUILD SUCCESS |
| Após o refactoring 2 | 65 | 0 | 0 | 0 | BUILD SUCCESS |

Os novos testes passaram também antes de cada refactoring correspondente. Eles verificam as validações de abertura, as 16 combinações de status e os casos nulos, incluindo mensagens de erro e ausência de persistência nas rejeições.

O JaCoCo 0.8.13 gerou o relatório em `target/site/jacoco/index.html`. A cobertura global de linhas passou de 65,78% (123/187) para 70,47% (136/193), e a de ramificações passou de 70,59% para 96,55%. Os resultados dos testes ficam em `target/surefire-reports/`.

A primeira tentativa no sandbox teve 17 erros de inicialização do Mockito/Byte Buddy ao anexar o agente à JVM. As execuções acima ocorreram fora do sandbox, sem alterar código ou configuração para contornar esse problema.

O Jenkinsfile mantém as etapas Validate, Build, Tests and Coverage, Package e Deploy Local. A execução real do Jenkins não foi verificada por falta de endereço e acesso disponíveis neste ambiente; os resultados locais não comprovam o estado do pipeline.

Após integrar a `main` para resolver o conflito do PR #32, `mvn clean test` aprovou 87 testes, sem falhas, erros ou testes ignorados. O JaCoCo gerou o relatório com cobertura global de linhas de 70,65% (219/310) e de ramificações de 67,52% (79/117). Esses resultados incluem o código e os testes adicionados pelos demais integrantes na `main`.

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

### GitHub

https://github.com/C14-Inatel-2026-2/Sistema-de-Chamado

### Jira

https://ppedrocesarmaiaa.atlassian.net/jira/software/projects/SCM/boards/1/backlog
