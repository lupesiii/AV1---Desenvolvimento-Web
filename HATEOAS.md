# Contrato de hipermídia implementado

## Negociação de conteúdo

| Accept | Resultado |
| --- | --- |
| `application/hal+json` | HAL: dados, `_links` e `_embedded` nas coleções |
| `application/prs.hal-forms+json` | HAL-FORMS: HAL mais `_templates` para as ações de escrita disponíveis |
| `application/json`, sem Accept ou `*/*` | JSON nas rotas existentes; sem Accept ou `*/*` na raiz seleciona HAL |
| Tipo não suportado, como `application/xml` | `406 Not Acceptable` |

As respostas incluem `Vary: Accept`. O ponto de entrada `GET /` oferece HAL e HAL-FORMS. As rotas de escrita mantêm os DTOs e as validações anteriores; o corpo de POST/PATCH usa JSON. POST retorna `201` sem corpo e `Location`. PATCH e DELETE retornam `204` sem corpo. Consulte `Location` usando o mesmo `Accept` para continuar a navegação.

As representações JSON de cliente preservam `endereco` e `telefones` como dados incorporados. Endereço e telefone são convertidos para DTOs sem referência recursiva ao proprietário. As representações HAL de cliente expõem os relacionamentos por links.

## Navegação

`GET /` anuncia `self`, `clientes` e `documentos`. Cada recurso ou coleção possui `self`.

| Representação | Relações adicionais |
| --- | --- |
| Cliente | `clientes`, `telefones`, `documentos`; `endereco` somente se cadastrado |
| Endereço | `cliente` |
| Telefone | `cliente`, `telefones` |
| Documento | `cliente`, `documentos` do proprietário |
| Telefones ou documentos de cliente | `cliente` |

A coleção global de documentos não possui um único proprietário e não anuncia cadastro: a criação deve partir da coleção de documentos de um cliente.

Os links são absolutos e usam a base e o contexto da requisição, sem host fixado no código. `self` do endereço usa o ID do cliente, mesmo quando o ID do endereço for diferente. Em implantação atrás de proxy, configure o tratamento de cabeçalhos encaminhados conforme sua infraestrutura, por exemplo `server.forward-headers-strategy=framework` quando o proxy confiável fornecer esses cabeçalhos.

As coleções usam `_embedded.clientes`, `_embedded.telefones` ou `_embedded.documentos`, sempre como arrays, inclusive quando vazias. Cada item tem os mesmos dados, links e ações da consulta individual.

Exemplo de coleção vazia de cliente existente:

```json
{
  "_embedded": { "telefones": [] },
  "_links": {
    "self": { "href": "http://localhost:8080/clientes/1/telefones" },
    "cliente": { "href": "http://localhost:8080/clientes/1" }
  }
}
```

Cliente inexistente retorna `404`, em vez de coleção vazia. Cliente existente sem endereço retorna `404` na consulta direta ao endereço e não anuncia a relação `endereco`.

## Descoberta de ações e RMM nível 3

Além da navegação de leitura, o consumidor pode solicitar HAL-FORMS para descobrir as transições de escrita. Cada template informa `method`, `contentType`, `properties` e, quando diferente de `self`, `target`. Se `target` estiver ausente, use `_links.self.href`.

| Representação | Ações disponíveis |
| --- | --- |
| Coleção de clientes | Cadastrar cliente |
| Cliente | Atualizar cliente, excluir cliente; cadastrar endereço somente quando ainda não possui endereço |
| Endereço | Atualizar e excluir endereço |
| Coleção de telefones do cliente | Cadastrar telefone |
| Telefone | Atualizar e excluir telefone |
| Coleção de documentos do cliente | Cadastrar documento |
| Documento | Atualizar e excluir documento |

A primeira ação é renderizada sob a chave `default` pelo Spring HATEOAS; as demais usam nomes como `excluirCliente` e `cadastrarEndereco`. O cliente deve interpretar os templates e seus métodos, sem depender da ordem das propriedades. HAL simples não inclui `_templates`: use HAL-FORMS para descoberta de escrita.

Exemplo parcial de cliente sem endereço, solicitado com `Accept: application/prs.hal-forms+json`:

```json
{
  "id": 1,
  "nome": "Ana",
  "nomeSocial": null,
  "dataNascimento": null,
  "_links": {
    "self": { "href": "http://localhost:8080/clientes/1" },
    "clientes": { "href": "http://localhost:8080/clientes" },
    "telefones": { "href": "http://localhost:8080/clientes/1/telefones" },
    "documentos": { "href": "http://localhost:8080/clientes/1/documentos" }
  },
  "_templates": {
    "default": {
      "method": "PATCH",
      "contentType": "application/json",
      "target": "http://localhost:8080/clientes",
      "properties": [
        { "name": "id", "type": "number" },
        { "name": "nome", "type": "text" },
        { "name": "nomeSocial", "type": "text" },
        { "name": "dataNascimento", "type": "date" }
      ]
    },
    "excluirCliente": { "method": "DELETE", "properties": [] }
  }
}
```

O exemplo omite a ação `cadastrarEndereco` e metadados de validação para facilitar a leitura. Os valores reais de `href` e `target` devem sempre ser obtidos da resposta.

Os PATCH legados continuam exigindo `id` (cliente/documento) ou `telefoneId` no corpo; o cadastro de telefone exige `clienteId`. Preencha esses campos com os dados do recurso/proprietário, além dos campos editados. O Spring HAL-FORMS omite `required` nos templates PATCH por padrão; isso não remove a validação do identificador no servidor. Essa convenção mantém a compatibilidade com os endpoints anteriores.

Fluxo completo sem construir URLs de escrita:

1. Consulte `/` com `Accept: application/prs.hal-forms+json`.
2. Siga `_links.clientes.href` e obtenha o template POST.
3. Envie JSON ao `target` do template ou a `self`, usando o método e o tipo de conteúdo informados.
4. Consulte `Location`. O cliente criado anuncia ações PATCH, DELETE e cadastro de endereço.
5. Execute o cadastro de endereço pelo template POST. A próxima consulta do cliente anuncia o link `endereco` e deixa de anunciar o cadastro.
6. Siga `telefones` e `documentos` para descobrir os formulários de cadastro associados ao proprietário.
7. Consulte o `Location` de cada criação para descobrir as ações de edição e exclusão do recurso.

## Dados, erros e integridade

Campos de cliente em HAL: `id`, `nome`, `nomeSocial`, `dataNascimento`. Datas usam `YYYY-MM-DD`; campos opcionais ausentes aparecem como `null`. Endereço e telefone expõem seus campos escalares. Documento expõe `id`, `tipo`, `numero`, `path` e `clienteId`. `path` é texto, sem ação de download.

Erros de negócio e validação preservam `titulo` e `mensagem`: `400` para entradas inválidas, `404` para ausência de recurso e `409` para conflitos reconhecidos. Respostas de erro não precisam de `self`. Conflitos de integridade retornam mensagens públicas, sem SQL ou detalhes internos. O servidor ainda pode retornar erros se o estado mudar após a descoberta de uma ação.

A exclusão de cliente publica um evento síncrono na transação. O módulo de documentos remove os registros do proprietário antes da exclusão, enquanto endereço e telefones seguem o cascade JPA existente. Isso evita documentos órfãos nas exclusões pela API sem criar dependência do módulo de cliente para o módulo de documento.

## Implementação e validação

Os assemblers montam `EntityModel` na camada HTTP; as coleções usam `CollectionModel` com wrappers de relação explícita. As entidades JPA não conhecem links. `ApiRoutes` centraliza os caminhos usados pelos controllers e pela montagem de links, preservando as fronteiras verificadas pelo Spring Modulith.

`RecordInputMetadata` adapta a descoberta dos DTOs `record` na versão 2.4 do Spring HATEOAS: os campos de entrada permanecem editáveis e `@Size` em strings é anunciado como limite de comprimento. As regras de obrigatoriedade e os padrões de validação dos DTOs são preservados.

Os testes de integração executam requisições com MockMvc e persistência H2 real, sem simular os serviços. Cobrem links HAL/HAL-FORMS, execução dos templates POST/PATCH/DELETE, ações condicionais, JSON legado, `Vary`, `406`, coleções vazias, erros, exclusão dos documentos e links com contexto de implantação. A suíte também inclui inicialização e verificação de modularidade.

```bash
./mvnw test
```

Referência da versão utilizada: [Spring HATEOAS 2.4.1 — HAL e HAL-FORMS](https://docs.spring.io/spring-hateoas/docs/2.4.1/reference/html/).
