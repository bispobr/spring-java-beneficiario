# API REST para Gerenciar beneficiários - Plano de saúde

## Descrição

Esta aplicação é uma API REST desenvolvida para gerir Beneficiários de um plano de saúde, oferecendo suporte às operações básicas de um CRUD:


## Tecnologias Utilizadas

- **Java + Spring Boot** – Framework principal da aplicação
- **Lombok (@Slf4j)** – Geração de logs
- **Tratamento de Exceções** - @RestControllerAdvice
- **Swagger** – Documentação interativa da API
- **Spring Boot Actuator** – Monitoramento e verificação de saúde da aplicação
- **H2 database** – Banco de dados relacional utilizado
- **Docker** – criação, implantação e gerenciamento de aplicações dentro de contêineres.

## Requisitos

- Java 21+
- Maven


## Executando o Projeto

1. Clone o repositório:

```bash
git https://github.com/bispobr/spring-java-beneficiario.git
```


## Como usar (Localmente)

1. Inicie a aplicação
2. A API está acessível através do endereço http://localhost:8080
3. A documentação da API está acessível através do Link http://localhost:8080/swagger-ui/index.html#/

## Como Rodar em um Container (Opcional)

1. Construa o projeto

```bash
mvn clean package 
```

2. Gere a Imagem Docker, com o Docker  instalado execute:


```bash
docker build -t beneficiario . 
```

3. Execute o Container

```bash
docker run -p 8080:8080 beneficiario
```

## API Endpoints
API contem os seguintes endpoints:

```http request
POST /beneficiarios - Registra  um novo beneficiario
Content-Type: application/json

{
  "nome": "string",
  "telefone": "string",
  "dataNascimento": "1980-05-03",
  "documentos": [
    {
      "tipoDocumento": "string",
      "descricao": "string"
    }
  ]
}
```
| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `nome` | `String` | **Obrigatório**.
| `telefone` | `String` | **Obrigatório**. 
| `dataNascimento` | `String` | **Obrigatório**. 
| `tipoDocumento` | `String` | **Obrigatório**. 
| `descricao` | `String` | **Obrigatório**. 



```http request
GET /beneficiarios -  Lista todos os beneficiários
```

```http request
GET /beneficiarios/{id} -  Lista beneficiario por id
```



```http request
PUT /beneficiarios/{id} - Atualizar um beneficiário existente
Content-Type: application/json

{
  "nome": "string",
  "telefone": "string",
  "dataNascimento": "1980-05-03",
  "documentos": [
    {
      "tipoDocumento": "string",
      "descricao": "string"
    }
  ]
}
```



```http request
DELETE /beneficiarios/{id} - Remover  produto de id especificado.
```

