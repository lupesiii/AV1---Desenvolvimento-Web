# Automanager

Microsserviço Spring Boot para cadastro dos dados do cliente (clientes, documentos, endereços e telefones).

## Requisitos

- Java Development Kit (JDK) 21 ou superior. Confirme com `java -version`;
- Acesso local pela porta padrão do Spring Boot (`8080`);
- Utilização do H2 (as informações do banco ficam armazenadas na memória da sessão).

## Como executar

Abra um terminal na raiz do repositório.

No Linux ou macOS, permita a execução do wrapper (se necessário) e inicie a aplicação:

```bash
chmod +x ./mvnw
./mvnw spring-boot:run
```

No Windows, use o Prompt de Comando ou PowerShell:

```bat
mvnw.cmd spring-boot:run
```

Ou, no VSCode, execute a aplicação pela interface gráfica (botão de play).

Espere a mensagem de inicialização do Spring Boot. A API ficará disponível em `http://localhost:8080`.

## Endpoints

Os identificadores nas rotas devem ser números maiores que zero.

### Clientes

| Método e rota                          | Ação                                    |
| -------------------------------------- | --------------------------------------- |
| `GET /clientes` / `GET /clientes/{id}` | Lista / consulta                        |
| `POST /clientes`                       | Cadastra cliente.                       |
| `PUT /clientes`                        | Atualiza cliente; informe `id` no JSON. |
| `DELETE /clientes/{id}`                | Exclui o cliente indicado na rota.      |

Campos do cadastro (`POST`):

| Campo            | Obrigatório | Observação                                                          |
| ---------------- | ----------- | ------------------------------------------------------------------- |
| `nome`           | Sim         | Até 255 caracteres.                                                 |
| `nomeSocial`     | Não         | Até 255 caracteres.                                                 |
| `dataNascimento` | Não         | Deve estar no passado. A `dataCadastro` é preenchida pelo servidor. |

Documentos, endereço e telefones **não** são enviados no cadastro do cliente; use os endpoints específicos depois de obter o `id` (cabeçalho `Location` da resposta `201`).

Exemplo de cadastro:

```bash
curl -X POST http://localhost:8080/clientes \
  -H 'Content-Type: application/json' \
  -d '{
    "nome": "Ana Souza",
    "nomeSocial": "Ana",
    "dataNascimento": "1995-04-12T00:00:00.000+00:00"
  }'
```

Consultas e exclusão:

```bash
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X DELETE http://localhost:8080/clientes/1
```

Atualização (`PUT /clientes`): envie o `id` no corpo. Os demais campos são opcionais; só o que for informado é alterado.

```bash
curl -X PUT http://localhost:8080/clientes \
  -H 'Content-Type: application/json' \
  -d '{
    "id": 1,
    "nome": "Ana Souza Silva",
    "nomeSocial": "Ana",
    "dataNascimento": "1995-04-12T00:00:00.000+00:00"
  }'
```

### Documentos

O número do documento é único no banco. Cada documento pertence a um cliente (`clienteId`).

| Método e rota                              | Ação                                       |
| ------------------------------------------ | ------------------------------------------ |
| `GET /documentos` / `GET /documentos/{id}` | Lista todos / consulta pelo identificador. |
| `GET /documentos/cliente/{clienteId}`      | Lista os documentos do cliente.            |
| `POST /documentos`                         | Cadastra documento.                        |
| `PUT /documentos`                          | Atualiza; informe `id` no corpo.           |
| `DELETE /documentos/{id}`                  | Exclui pelo identificador.                 |

Campos do cadastro (`POST`):

| Campo       | Obrigatório | Observação                          |
| ----------- | ----------- | ----------------------------------- |
| `tipo`      | Sim         | Até 255 caracteres.                 |
| `numero`    | Sim         | Até 255 caracteres; único.          |
| `path`      | Não         | Até 255 caracteres.                 |
| `clienteId` | Sim         | Identificador do cliente existente. |

```bash
curl -X POST http://localhost:8080/documentos \
  -H 'Content-Type: application/json' \
  -d '{"tipo":"CPF","numero":"12345678900","path":"/docs/cpf.pdf","clienteId":1}'
curl http://localhost:8080/documentos
curl http://localhost:8080/documentos/1
curl http://localhost:8080/documentos/cliente/1
curl -X PUT http://localhost:8080/documentos \
  -H 'Content-Type: application/json' \
  -d '{"id":1,"tipo":"CPF","numero":"12345678900","path":"/docs/cpf.pdf"}'
curl -X DELETE http://localhost:8080/documentos/1
```

No `PUT`, `tipo`, `numero` e `path` são opcionais; só o que for informado é alterado.

### Endereços

Cada cliente possui no máximo um endereço.

| Método e rota                    | Ação                                                          |
| -------------------------------- | ------------------------------------------------------------- |
| `GET /enderecos/cliente/{id}`    | Consulta o endereço do cliente.                               |
| `POST /enderecos`                | Cadastra endereço para o cliente informado no JSON.           |
| `PUT /enderecos`                 | Atualiza o endereço do cliente; informe `clienteId` no corpo. |
| `DELETE /enderecos/cliente/{id}` | Remove o endereço do cliente.                                 |

Campos do cadastro (`POST`):

| Campo          | Obrigatório | Observação                          |
| -------------- | ----------- | ----------------------------------- |
| `clienteId`    | Sim         | Identificador do cliente existente. |
| `cidade`       | Sim         | Até 255 caracteres.                 |
| `rua`          | Sim         | Até 255 caracteres.                 |
| `numero`       | Sim         | Número do imóvel (texto, até 255).  |
| `estado`       | Não         | Até 255 caracteres.                 |
| `bairro`       | Não         | Até 255 caracteres.                 |
| `codigoPostal` | Não         | Formato `00000-000` ou `00000000`.  |

```bash
curl -X POST http://localhost:8080/enderecos \
  -H 'Content-Type: application/json' \
  -d '{
    "clienteId": 1,
    "estado": "SP",
    "cidade": "São Paulo",
    "bairro": "Centro",
    "rua": "Rua A",
    "numero": "100",
    "codigoPostal": "01000-000"
  }'
curl http://localhost:8080/enderecos/cliente/1
curl -X PUT http://localhost:8080/enderecos \
  -H 'Content-Type: application/json' \
  -d '{
    "clienteId": 1,
    "estado": "SP",
    "cidade": "São Paulo",
    "bairro": "Centro",
    "rua": "Rua B",
    "numero": "200",
    "codigoPostal": "01000-000"
  }'
curl -X DELETE http://localhost:8080/enderecos/cliente/1
```

No `PUT`, além de `clienteId`, os demais campos são opcionais; só o que for informado é alterado.

### Telefones

Não é permitido cadastrar o mesmo par DDD + número mais de uma vez.

| Método e rota                        | Ação                                                |
| ------------------------------------ | --------------------------------------------------- |
| `GET /telefones/cliente/{clienteId}` | Lista os telefones do cliente.                      |
| `POST /telefones`                    | Cadastra telefone para o cliente informado no JSON. |
| `PUT /telefones`                     | Atualiza o telefone; informe `telefoneId` no corpo. |
| `DELETE /telefones/{telefoneId}`     | Exclui pelo identificador do telefone.              |

Campos do cadastro (`POST`):

| Campo       | Obrigatório | Observação                          |
| ----------- | ----------- | ----------------------------------- |
| `clienteId` | Sim         | Identificador do cliente existente. |
| `ddd`       | Sim         | Exatamente 2 dígitos.               |
| `numero`    | Sim         | 8 ou 9 dígitos, somente números.    |

```bash
curl -X POST http://localhost:8080/telefones \
  -H 'Content-Type: application/json' \
  -d '{"clienteId":1,"ddd":"11","numero":"999991234"}'
curl http://localhost:8080/telefones/cliente/1
curl -X PUT http://localhost:8080/telefones \
  -H 'Content-Type: application/json' \
  -d '{"telefoneId":1,"ddd":"11","numero":"988881234"}'
curl -X DELETE http://localhost:8080/telefones/1
```

No `PUT`, `ddd` e `numero` são opcionais; só o que for informado é alterado.
