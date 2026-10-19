# Automanager

API Spring Boot para cadastro de clientes, endereços, telefones e documentos. A API oferece hipermídia para o nível 3 do modelo de maturidade de Richardson (RMM): HAL para navegação e HAL-FORMS para descobrir e executar as operações de escrita.

## Executar e testar

Requisitos: JDK 21 ou superior. O H2 é criado em memória; os dados são perdidos ao encerrar a aplicação.

```bash
./mvnw spring-boot:run
./mvnw test
```

No Windows, use `mvnw.cmd`. A aplicação fica disponível em `http://localhost:8080`.

## Descobrir a API

O ponto de entrada é `GET /`. Siga os endereços em `_links`, sem montar URLs a partir dos identificadores.

```bash
curl http://localhost:8080/ -H 'Accept: application/hal+json'
curl http://localhost:8080/clientes -H 'Accept: application/prs.hal-forms+json'
```

- `application/hal+json`: recursos com `_links` e coleções com `_embedded`.
- `application/prs.hal-forms+json`: também inclui `_templates`, com método, destino e campos das ações disponíveis.
- `application/json`, ausência de `Accept` ou `*/*`: mantém JSON nas rotas existentes. Na raiz, ausência de `Accept` ou `*/*` seleciona HAL.

POST e PATCH recebem `Content-Type: application/json`. POST retorna `201 Created` com `Location` consultável; PATCH e DELETE retornam `204 No Content`.

Veja o [contrato implementado e os exemplos de navegação e ações](HATEOAS.md).

## Rotas

Todos os identificadores devem ser positivos. As rotas existentes de escrita foram mantidas.

| Recurso | Consultas | Cadastro | Atualização | Exclusão |
| --- | --- | --- | --- | --- |
| Clientes | `GET /clientes`, `GET /clientes/{id}` | `POST /clientes` | `PATCH /clientes` (`id` no corpo) | `DELETE /clientes/{id}` |
| Endereço | `GET /clientes/{id}/endereco` | `POST /clientes/{id}/endereco` | `PATCH /clientes/{id}/endereco` | `DELETE /clientes/{id}/endereco` |
| Telefones | `GET /clientes/{clienteId}/telefones`, `GET /telefones/{telefoneId}` | `POST /telefones` (`clienteId` no corpo) | `PATCH /telefones` (`telefoneId` no corpo) | `DELETE /telefones/{telefoneId}` |
| Documentos | `GET /documentos`, `GET /documentos/{id}`, `GET /clientes/{clienteId}/documentos` | `POST /clientes/{clienteId}/documentos` | `PATCH /documentos` (`id` no corpo) | `DELETE /documentos/{id}` |

## Exemplos de cadastro

Use o identificador do cliente retornado no `Location` no lugar de `1`.

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

No cadastro de cliente, somente `nome` é obrigatório. A data de nascimento é opcional e deve estar no passado, no formato `YYYY-MM-DD`. Os campos de endereço do exemplo são obrigatórios. DDD tem dois dígitos e telefone tem oito ou nove; o par DDD + número é único. Documentos exigem `tipo`, `numero` único e `path` (texto, sem endpoint de download).

PATCH altera somente os campos informados, além do identificador obrigatório quando ele faz parte do corpo. Telefones, CEP, número de imóvel e número de documento são strings para preservar zeros iniciais.

## Verificação

A suíte cobre inicialização, fronteiras entre módulos e testes HTTP com banco H2 real: navegação por links, coleções vazias, ações de cadastro/edição/exclusão, ações condicionais de endereço, erros, compatibilidade JSON e prefixos de implantação. A exclusão do cliente também remove seus documentos na mesma transação, evitando links para proprietários inexistentes.
