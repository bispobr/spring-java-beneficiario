# API REST de Beneficiários — Plano de Saúde

API REST desenvolvida com Java e Spring Boot para gerenciamento de beneficiários de um plano de saúde.

A aplicação disponibiliza operações de cadastro, consulta, atualização e exclusão de beneficiários, incluindo informações de contato, data de nascimento e documentos associados.

## Funcionalidades

- Cadastro de beneficiários
- Listagem de beneficiários
- Consulta de beneficiário por ID
- Atualização de beneficiário
- Exclusão de beneficiário
- Cadastro de documentos associados ao beneficiário
- Validação dos dados de entrada
- Tratamento global de exceções
- Documentação da API com Swagger/OpenAPI
- Monitoramento com Spring Boot Actuator
- Logs estruturados na aplicação
- Persistência utilizando banco de dados relacional
- Execução em container Docker

## Tecnologias

- Java 21+
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Validation
- H2 Database
- Spring Boot Actuator
- Swagger/OpenAPI
- Lombok
- Maven
- Docker

## Requisitos

- Java 21+
- Maven
- Docker (opcional)

## Executando o projeto

Clone o repositório:

```bash
git clone https://github.com/bispobr/spring-java-beneficiario.git
cd spring-java-beneficiario
```

Execute a aplicação com Maven:

```bash
mvn spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

## Swagger / OpenAPI

Com a aplicação em execução, a documentação interativa da API pode ser acessada em:

```text
http://localhost:8080/swagger-ui/index.html
```

## Actuator

Endpoint de saúde da aplicação:

```text
http://localhost:8080/actuator/health
```

## API Endpoints

### Criar beneficiário

```http
POST /beneficiarios
Content-Type: application/json
```

Exemplo:

```json
{
  "nome": "João da Silva",
  "telefone": "11999999999",
  "dataNascimento": "1980-05-03",
  "documentos": [
    {
      "tipoDocumento": "CPF",
      "descricao": "12345678900"
    }
  ]
}
```

### Listar beneficiários

```http
GET /beneficiarios
```

Retorna os beneficiários cadastrados.

### Consultar beneficiário por ID

```http
GET /beneficiarios/{id}
```

### Atualizar beneficiário

```http
PUT /beneficiarios/{id}
Content-Type: application/json
```

Exemplo:

```json
{
  "nome": "João da Silva",
  "telefone": "11988888888",
  "dataNascimento": "1980-05-03",
  "documentos": [
    {
      "tipoDocumento": "CPF",
      "descricao": "12345678900"
    }
  ]
}
```

### Excluir beneficiário

```http
DELETE /beneficiarios/{id}
```

## Modelo de dados

O beneficiário possui informações básicas e pode possuir documentos associados:

```text
Beneficiário
├── id
├── nome
├── telefone
├── dataNascimento
└── documentos
     ├── tipoDocumento
     └── descricao
```

## Fluxo da API

```text
Cliente
   │
   ▼
API REST
   │
   ▼
Validação
   │
   ▼
Camada de serviço
   │
   ▼
Persistência
   │
   ▼
Banco de dados
```

## Docker

Gere o pacote da aplicação:

```bash
mvn clean package
```

Gere a imagem Docker:

```bash
docker build -t beneficiario .
```

Execute o container:

```bash
docker run -p 8080:8080 beneficiario
```

## Testes

Execute os testes automatizados com:

```bash
mvn test
```

## Status

Projeto desenvolvido para praticar a construção de APIs REST com Spring Boot, incluindo operações CRUD, validação, tratamento de exceções, documentação OpenAPI, monitoramento e execução em containers.
