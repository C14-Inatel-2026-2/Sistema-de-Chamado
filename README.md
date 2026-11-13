# Sistema de Chamados de Manutenção

Projeto da disciplina C14 - Engenharia de Software.

## Tema

Aplicação web para registrar, acompanhar e gerenciar chamados de manutenção relacionados a salas, laboratórios e equipamentos.

## Tecnologias iniciais

- Java 21
- Spring Boot 4.1.1
- Maven
- Spring MVC
- Thymeleaf
- Spring Data JPA
- H2
- PostgreSQL
- JUnit 5

## Integrantes

- Eduardo Nogueira Belarmino - GES 597
- Pedro Henrique dos Santos Araujo - GES 502
- Felipe da Silva Inácio - GES 181
- Cauã de Souza Flauzino - GES 410
- Allan Pinto Nogueira - GES 671
- Pedro Cesar Maia - GEC 1828 - Líder

## Executar

```bash
mvn spring-boot:run
```

Acesse:

```text
http://localhost:8080/
```

## Testes

```bash
mvn test
```

## Refactorings

### Allan

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

Validação local em 13/11/2026, com Java 21 e `mvn clean test`:

| Estado | Testes | Falhas | Erros | Ignorados | Resultado |
| --- | --- | --- | --- | --- | --- |
| Antes dos refactorings | 30 | 0 | 0 | 0 | BUILD SUCCESS |
| Após o refactoring 1 | 40 | 0 | 0 | 0 | BUILD SUCCESS |
| Após o refactoring 2 | 65 | 0 | 0 | 0 | BUILD SUCCESS |

Os novos testes passaram também antes de cada refactoring correspondente. Eles verificam as validações de abertura, as 16 combinações de status e os casos nulos, incluindo mensagens de erro e ausência de persistência nas rejeições.

O JaCoCo 0.8.13 gerou o relatório em `target/site/jacoco/index.html`. A cobertura global de linhas passou de 65,78% (123/187) para 70,47% (136/193), e a de ramificações passou de 70,59% para 96,55%. Os resultados dos testes ficam em `target/surefire-reports/`.

A primeira tentativa no sandbox teve 17 erros de inicialização do Mockito/Byte Buddy ao anexar o agente à JVM. As execuções acima ocorreram fora do sandbox, sem alterar código ou configuração para contornar esse problema.

O Jenkinsfile mantém as etapas Validate, Build, Tests and Coverage, Package e Deploy Local. A execução real do Jenkins não foi verificada por falta de endereço e acesso disponíveis neste ambiente; os resultados locais não comprovam o estado do pipeline.
