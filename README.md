# Automanager

API REST para cadastro e gerenciamento de clientes, endereços, telefones e documentos. O projeto é organizado em módulos com Spring Modulith e oferece representações JSON, HAL e HAL-FORMS. A hipermídia permite navegar entre os recursos e descobrir as operações de escrita, seguindo o nível 3 do modelo de maturidade de Richardson (RMM).

## Tecnologias

| Tecnologia | Uso no projeto |
| --- | --- |
| Java 21 | Versão definida para compilação |
| Spring Boot 3.4.13 | Inicialização da aplicação e API HTTP com Spring MVC |
| Spring Modulith 1.3.0 | Organização e verificação das fronteiras entre módulos |
| Spring Data JPA / Hibernate | Persistência e relacionamentos entre entidades |
| H2 | Banco de dados em memória |
| Spring HATEOAS | Links HAL e ações HAL-FORMS |
| Jakarta Bean Validation | Validação dos corpos e parâmetros das requisições |
| Lombok | Geração de métodos nas entidades e DTOs de erro |
| JUnit 5 e Spring Boot Test | Testes de contexto e modularidade |

## Executar

Utilize o JDK 21 e o Maven Wrapper incluído no repositório, configurado com Maven 3.8.4. Não é necessário instalar o Maven separadamente. A primeira execução precisa de acesso à internet para baixar o Maven e as dependências.

Na raiz do projeto:

```bash
./mvnw spring-boot:run
```

No Windows, use `.\mvnw.cmd` no lugar de `./mvnw`. A aplicação fica disponível em [http://localhost:8080](http://localhost:8080).

Para gerar e executar o JAR:

```bash
./mvnw clean package
java -jar target/automanager-0.0.1-SNAPSHOT.jar
```

O arquivo [`application.properties`](src/main/resources/application.properties) está sem configurações específicas. Com as dependências atuais, o Spring Boot configura o H2 em memória e cria o esquema a partir das entidades JPA. Os dados são perdidos ao encerrar a aplicação; nenhum banco externo precisa ser preparado.

Configurações podem ser adicionadas ao arquivo ou fornecidas externamente. Por exemplo, em Bash, para mudar a porta:

```bash
SERVER_PORT=8081 ./mvnw spring-boot:run
```

## Organização do projeto

```text
src/main/java/com/autobots/automanager/
├── AutobotsApplication.java       # Inicialização do Spring Boot
├── ApiController.java             # Ponto de entrada da API
├── HypermediaConfiguration.java   # Negociação de conteúdo e suporte à hipermídia
├── cliente/                       # Clientes, endereços e telefones
│   ├── controllers/
│   ├── domain/
│   ├── models/                    # DTOs, mapeadores, atualizadores e assemblers
│   ├── repositories/
│   └── services/
├── documento/                     # Documentos vinculados a clientes
│   ├── controllers/
│   ├── domain/
│   ├── models/
│   ├── repositories/
│   └── services/
└── common/                        # Rotas, utilitários e metadados de hipermídia
```

O módulo `cliente` gerencia um endereço e vários telefones por cliente. O módulo `documento` mantém o vínculo pelo campo `clienteId` e consulta a existência do cliente por meio de `ClienteFacade`, sem acessar suas entidades ou repositórios internos.

Ao excluir um cliente, endereço e telefones são removidos em cascata pelo JPA. O evento síncrono `ClienteExcluido` aciona a remoção dos documentos na mesma transação.

## Descobrir a API

O ponto de entrada é `GET /`. Siga os endereços em `_links`, sem montar URLs a partir dos identificadores.

```bash
curl http://localhost:8080/ -H 'Accept: application/hal+json'
curl http://localhost:8080/clientes -H 'Accept: application/prs.hal-forms+json'
```

| Cabeçalho `Accept` | Representação |
| --- | --- |
| `application/json` | JSON nas rotas de clientes, endereços, telefones e documentos |
| `application/hal+json` | Recursos com `_links` e coleções com `_embedded` |
| `application/prs.hal-forms+json` | HAL mais `_templates`, com método, destino e campos das ações disponíveis |
| Ausente ou `*/*` | HAL na raiz e JSON nas demais rotas |

A raiz aceita HAL e HAL-FORMS. Tipos não suportados retornam `406 Not Acceptable`, e as respostas incluem `Vary: Accept`.

No JSON de cliente, endereço e telefones aparecem incorporados. Em HAL/HAL-FORMS, esses relacionamentos são acessados por links. As coleções HAL mantêm arrays em `_embedded` mesmo quando vazias. O link de endereço só aparece quando há endereço cadastrado; a ação HAL-FORMS de cadastro só aparece quando ele ainda não existe.

POST e PATCH recebem `Content-Type: application/json`. Os cadastros retornam `201 Created`, sem corpo, com `Location` para consultar o recurso criado. PATCH e DELETE retornam `204 No Content`.

Veja o [contrato implementado e os exemplos de navegação e ações](HATEOAS.md).

## Rotas

Todos os identificadores devem ser positivos. `GET /` é o ponto de entrada para descoberta das coleções de clientes e documentos.

| Recurso | Consultas | Cadastro | Atualização | Exclusão |
| --- | --- | --- | --- | --- |
| Clientes | `GET /clientes`, `GET /clientes/{id}` | `POST /clientes` | `PATCH /clientes` (`id` no corpo) | `DELETE /clientes/{id}` |
| Endereço | `GET /clientes/{id}/endereco` | `POST /clientes/{id}/endereco` | `PATCH /clientes/{id}/endereco` | `DELETE /clientes/{id}/endereco` |
| Telefones | `GET /clientes/{clienteId}/telefones`, `GET /telefones/{telefoneId}` | `POST /telefones` (`clienteId` no corpo) | `PATCH /telefones` (`telefoneId` no corpo) | `DELETE /telefones/{telefoneId}` |
| Documentos | `GET /documentos`, `GET /documentos/{id}`, `GET /clientes/{clienteId}/documentos` | `POST /clientes/{clienteId}/documentos` | `PATCH /documentos` (`id` no corpo) | `DELETE /documentos/{id}` |

As rotas de endereço recebem o **ID do cliente**, que pode ser diferente do ID do endereço. O cadastro de documentos parte de um cliente; a coleção global `/documentos` permite listar os documentos de todos os clientes.

## Exemplos de uso

Os exemplos abaixo usam Bash e `curl`.

### Cadastro

Cadastre o cliente primeiro. Nas requisições seguintes, substitua `1` pelo identificador indicado no `Location` retornado.

```bash
curl -i -X POST http://localhost:8080/clientes \
  -H 'Content-Type: application/json' \
  -d '{"nome":"Ana Souza","nomeSocial":"Ana","dataNascimento":"1995-04-12"}'

curl -i -X POST http://localhost:8080/clientes/1/endereco \
  -H 'Content-Type: application/json' \
  -d '{"estado":"SP","cidade":"São Paulo","bairro":"Centro","rua":"Rua A","numero":"100","codigoPostal":"01000-000"}'

curl -i -X POST http://localhost:8080/telefones \
  -H 'Content-Type: application/json' \
  -d '{"clienteId":1,"ddd":"11","numero":"999991234"}'

curl -i -X POST http://localhost:8080/clientes/1/documentos \
  -H 'Content-Type: application/json' \
  -d '{"tipo":"CPF","numero":"12345678900","path":"/docs/cpf.pdf"}'
```

### Consulta, atualização e exclusão

```bash
curl http://localhost:8080/clientes/1 -H 'Accept: application/json'

curl -i -X PATCH http://localhost:8080/clientes \
  -H 'Content-Type: application/json' \
  -d '{"id":1,"nome":"Ana Silva"}'

curl -i -X PATCH http://localhost:8080/clientes/1/endereco \
  -H 'Content-Type: application/json' \
  -d '{"numero":"101"}'

curl -i -X PATCH http://localhost:8080/telefones \
  -H 'Content-Type: application/json' \
  -d '{"telefoneId":1,"numero":"988881234"}'

curl -i -X PATCH http://localhost:8080/documentos \
  -H 'Content-Type: application/json' \
  -d '{"id":1,"path":"/docs/cpf-atualizado.pdf"}'

curl -i -X DELETE http://localhost:8080/telefones/1
```

Para telefone e documento, utilize os respectivos IDs obtidos nos cadastros, que são independentes do ID do cliente.

## Validações e regras

| Recurso | Regras de cadastro |
| --- | --- |
| Cliente | `nome` obrigatório, com até 255 caracteres; `nomeSocial` opcional, com até 255 caracteres; `dataNascimento` opcional e no passado, no formato `YYYY-MM-DD` |
| Endereço | Cliente existente; `estado`, `cidade`, `bairro`, `rua`, `numero` e `codigoPostal` obrigatórios; os cinco primeiros campos aceitam até 255 caracteres, e o CEP aceita `00000-000` ou `00000000` |
| Telefone | `clienteId` de cliente existente; `ddd` com dois dígitos e `numero` com oito ou nove dígitos; o cadastro rejeita a repetição do par DDD + número, inclusive entre clientes diferentes |
| Documento | Cliente existente; `tipo`, `numero` e `path` obrigatórios, com até 255 caracteres; `numero` único entre todos os documentos |

PATCH exige o identificador quando ele faz parte do corpo e altera somente os campos de dados informados. Nos campos opcionais, valores omitidos, nulos ou strings em branco são ignorados; espaços nas extremidades das strings são removidos antes da atualização. O PATCH atual não permite limpar um campo atribuindo `null`.

DDD, telefone, CEP, número de imóvel e número de documento são strings para preservar zeros iniciais. O campo `path` do documento armazena apenas texto; não há operação de upload ou download de arquivos.

## Respostas e erros

| Status HTTP | Situação |
| --- | --- |
| `200 OK` | Consulta realizada; listagens JSON retornam arrays, inclusive vazios |
| `201 Created` | Recurso cadastrado; endereço de consulta informado em `Location` |
| `204 No Content` | Atualização ou exclusão concluída |
| `400 Bad Request` | Violação das validações dos campos ou identificador não positivo |
| `404 Not Found` | Cliente, endereço, telefone ou documento não encontrado |
| `406 Not Acceptable` | Representação solicitada em `Accept` não suportada pela rota |
| `409 Conflict` | Telefone já cadastrado ou violação de integridade, como número de documento repetido |

Os erros tratados de negócio e validação usam os campos `titulo` e `mensagem`. Por exemplo, ao cadastrar um cliente sem nome:

```json
{
  "titulo": "Dados Inválidos",
  "mensagem": "nome: o nome é obrigatório"
}
```

Um cliente existente sem telefones ou documentos retorna uma coleção vazia. Consultar essas coleções para um cliente inexistente retorna `404`. A consulta direta de um endereço ainda não cadastrado também retorna `404`.

## Testes

```bash
./mvnw test
```

Os testes versionados são:

- `AutomanagerApplicationTests`: verifica a inicialização do contexto Spring Boot.
- `ModularityTests`: verifica as fronteiras entre módulos com `ApplicationModules.verify()`.

O arquivo local `HypermediaIntegrationTests.java`, quando presente, também é executado pelo Maven, mas está ignorado no `.gitignore` e não acompanha um clone do repositório. Os detalhes do contrato de hipermídia e exemplos de navegação estão em [`HATEOAS.md`](HATEOAS.md).
