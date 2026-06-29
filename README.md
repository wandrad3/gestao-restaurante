# Ã°Å¸ÂÂ½Ã¯Â¸Â GestÃƒÂ£o de Restaurantes API - Tech Challenge Fase 2

Sistema de gestÃƒÂ£o para restaurantes desenvolvido como parte do Tech Challenge
FIAP. O backend permite gerenciar tipos de usuÃƒÂ¡rio, usuÃƒÂ¡rios, restaurantes e
itens de cardÃƒÂ¡pio, com validaÃƒÂ§ÃƒÂ£o, tratamento de erros, documentaÃƒÂ§ÃƒÂ£o Swagger,
testes automatizados e infraestrutura Docker.

## Ã°Å¸â€œâ€¹ ÃƒÂndice

- [CaracterÃƒÂ­sticas](#-caracterÃƒÂ­sticas)
- [PrÃƒÂ©-requisitos](#-prÃƒÂ©-requisitos)
- [ExecuÃƒÂ§ÃƒÂ£o rÃƒÂ¡pida com Docker](#-execuÃƒÂ§ÃƒÂ£o-rÃƒÂ¡pida-com-docker)
- [ExecuÃƒÂ§ÃƒÂ£o local](#-execuÃƒÂ§ÃƒÂ£o-local)
- [Estrutura do projeto](#-estrutura-do-projeto)
- [Endpoints disponÃƒÂ­veis](#-endpoints-disponÃƒÂ­veis)
- [Banco de dados](#Ã¯Â¸Â-banco-de-dados)
- [Exemplos de uso](#-exemplos-de-uso)
- [Erros comuns](#-erros-comuns)
- [DocumentaÃƒÂ§ÃƒÂ£o Swagger](#-documentaÃƒÂ§ÃƒÂ£o-swagger)
- [Testando a aplicaÃƒÂ§ÃƒÂ£o](#-testando-a-aplicaÃƒÂ§ÃƒÂ£o)
- [Pipeline CI e fluxo de branches](#-pipeline-ci-e-fluxo-de-branches)
- [ValidaÃƒÂ§ÃƒÂµes implementadas](#-validaÃƒÂ§ÃƒÂµes-implementadas)
- [Suporte e problemas](#-suporte-e-problemas)

## Ã¢Å“Â¨ CaracterÃƒÂ­sticas

### Funcionalidades principais

- Ã¢Å“â€¦ GestÃƒÂ£o de tipos de usuÃƒÂ¡rio: cadastro, consulta, atualizaÃƒÂ§ÃƒÂ£o e exclusÃƒÂ£o.
- Ã¢Å“â€¦ GestÃƒÂ£o de usuÃƒÂ¡rios: CRUD, alteraÃƒÂ§ÃƒÂ£o de senha e associaÃƒÂ§ÃƒÂ£o com tipo.
- Ã¢Å“â€¦ Busca por nome: parcial e sem diferenciaÃƒÂ§ÃƒÂ£o entre maiÃƒÂºsculas e minÃƒÂºsculas.
- Ã¢Å“â€¦ GestÃƒÂ£o de restaurantes: associaÃƒÂ§ÃƒÂ£o obrigatÃƒÂ³ria com um usuÃƒÂ¡rio responsÃƒÂ¡vel.
- Ã¢Å“â€¦ GestÃƒÂ£o de cardÃƒÂ¡pio: itens associados ao restaurante.
- Ã¢Å“â€¦ ValidaÃƒÂ§ÃƒÂµes robustas com Jakarta Validation.
- Ã¢Å“â€¦ Erros padronizados para payload invÃƒÂ¡lido, recurso inexistente e conflito.
- Ã¢Å“â€¦ API versionada sob `/api/v1`.
- Ã¢Å“â€¦ DocumentaÃƒÂ§ÃƒÂ£o interativa com Swagger/OpenAPI.
- Ã¢Å“â€¦ Migrations de banco de dados com Flyway.
- Ã¢Å“â€¦ Testes unitÃƒÂ¡rios e de integraÃƒÂ§ÃƒÂ£o com cobertura superior a 80%.
- Ã¢Å“â€¦ ExecuÃƒÂ§ÃƒÂ£o integrada com Docker Compose.

### Stack tecnolÃƒÂ³gico

- Java 17
- Spring Boot 3.4.5
- Spring Web
- Spring Data JPA e Hibernate
- Spring Security
- Jakarta Validation
- PostgreSQL 16
- Flyway
- Swagger/OpenAPI 3
- JUnit 5, Mockito, MockMvc e H2
- JaCoCo
- Docker e Docker Compose
- Maven

## Ã°Å¸â€Â§ PrÃƒÂ©-requisitos

### InstalaÃƒÂ§ÃƒÂ£o local

- Java 17+
- Maven 3.9+ ou Maven Wrapper
- PostgreSQL 16+
- Git

### Com Docker (recomendado)

- Docker 20.10+
- Docker Compose 2+

## Ã°Å¸Å¡â‚¬ ExecuÃƒÂ§ÃƒÂ£o rÃƒÂ¡pida com Docker

### 1. Clonar o repositÃƒÂ³rio

```bash
git clone <url-do-repositorio>
cd gestao-restaurante
```

### 2. Conferir as variÃƒÂ¡veis de ambiente

O arquivo `.env` jÃƒÂ¡ estÃƒÂ¡ versionado para facilitar a execuÃƒÂ§ÃƒÂ£o do projeto em IDE
e com Docker Compose durante a avaliaÃƒÂ§ÃƒÂ£o.

VariÃƒÂ¡veis disponÃƒÂ­veis:

| VariÃƒÂ¡vel | DescriÃƒÂ§ÃƒÂ£o | Exemplo |
|---|---|---|
| `POSTGRES_DB` | Nome do banco | `restaurant_db` |
| `POSTGRES_USER` | UsuÃƒÂ¡rio do banco | `app` |
| `POSTGRES_PASSWORD` | Senha do banco | `change-me` |
| `POSTGRES_PORT` | Porta externa do PostgreSQL | `5432` |
| `APP_PORT` | Porta externa da API | `8080` |
| `SPRING_DATASOURCE_URL` | JDBC para execuÃƒÂ§ÃƒÂ£o pela IDE | `jdbc:postgresql://localhost:5432/restaurant_db` |
| `SPRING_DATASOURCE_USERNAME` | UsuÃƒÂ¡rio JDBC para execuÃƒÂ§ÃƒÂ£o pela IDE | `app` |
| `SPRING_DATASOURCE_PASSWORD` | Senha JDBC para execuÃƒÂ§ÃƒÂ£o pela IDE | `change-me` |

### 3. Iniciar os serviÃƒÂ§os

```bash
docker compose up --build
```

O Docker Compose irÃƒÂ¡:

- Construir a aplicaÃƒÂ§ÃƒÂ£o em Java 17 com um Dockerfile multi-stage.
- Criar e iniciar o PostgreSQL 16.
- Executar o healthcheck do banco.
- Iniciar a aplicaÃƒÂ§ÃƒÂ£o somente quando o banco estiver saudÃƒÂ¡vel.
- Aplicar as migrations do Flyway.
- Persistir os dados no volume `postgres_data`.

### 4. Acessar a aplicaÃƒÂ§ÃƒÂ£o

- API base: `http://localhost:8080/api/v1`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

### 5. Parar os serviÃƒÂ§os

```bash
docker compose down
```

Para tambÃƒÂ©m remover os dados persistidos:

```bash
docker compose down -v
```

## Ã°Å¸â€™Â» ExecuÃƒÂ§ÃƒÂ£o local

Configure a conexÃƒÂ£o com o PostgreSQL ou carregue o `.env` pela IDE:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/restaurant_db
export SPRING_DATASOURCE_USERNAME=app
export SPRING_DATASOURCE_PASSWORD=change-me
```

Compile e execute:

```bash
./mvnw clean package
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

## Ã°Å¸â€œÂ Estrutura do projeto

```text
gestao-restaurante/
Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ src/
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ main/
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ java/com/fiap/gestaorestaurante/
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ Application.java
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ core/
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ controller/                # Controladores internos dos casos de uso
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ domain/                    # Entidades puras de negÃƒÂ³cio
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ dto/                       # Entradas dos casos de uso
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ exception/                 # ExceÃƒÂ§ÃƒÂµes de negÃƒÂ³cio
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ gateway/                   # Contratos para adapters externos
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ usecase/                   # Regras e fluxos da aplicaÃƒÂ§ÃƒÂ£o
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ infra/
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ config/                    # InjeÃƒÂ§ÃƒÂ£o dos use cases
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ database/                  # JPA, repositories, mappers e gateways
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ security/                  # JWT, senha e UserDetailsService
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ infrastructure/
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š       Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ web/
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š           Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ dto/                   # Contratos HTTP
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š           Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ exception/             # Erros REST centralizados
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€š           Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ *Controller.java       # Endpoints REST
Ã¢â€â€š   Ã¢â€â€š   Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ resources/
Ã¢â€â€š   Ã¢â€â€š       Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ application.yaml
Ã¢â€â€š   Ã¢â€â€š       Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ db/migration/
Ã¢â€â€š   Ã¢â€â€š           Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ V1__create_initial_schema.sql
Ã¢â€â€š   Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ test/
Ã¢â€â€š       Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ java/com/fiap/gestaorestaurante/
Ã¢â€â€š       Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ core/usecase/
Ã¢â€â€š       Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ infra/database/
Ã¢â€â€š       Ã¢â€â€š   Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ infrastructure/web/
Ã¢â€â€š       Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ resources/application.yaml
Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ docs/postman/
Ã¢â€â€š   Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ gestao-restaurante.postman_collection.json
Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ .env
Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ Dockerfile
Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ docker-compose.yml
Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ pom.xml
Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ README.md
```

### Arquitetura

O projeto adota uma separaÃƒÂ§ÃƒÂ£o inspirada na POC de Clean Architecture:

- **Core:** domÃƒÂ­nio puro, DTOs internos, gateways, use cases e controllers internos.
- **Infra:** adapters tÃƒÂ©cnicos para JPA, seguranÃƒÂ§a, JWT, senha e injeÃƒÂ§ÃƒÂ£o de dependÃƒÂªncias.
- **Infrastructure/Web:** controllers REST, payloads HTTP, Swagger, filtros e tratamento de erros.

Relacionamentos principais:

```text
UserType 1 Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬ N User
User     1 Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬ N Restaurant
Restaurant 1 Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬ N MenuItem
```

## Ã°Å¸â€œÂ¡ Endpoints disponÃƒÂ­veis

Todos os endpoints sÃƒÂ£o pÃƒÂºblicos nesta fase. A senha ÃƒÂ© armazenada com BCrypt e
nunca ÃƒÂ© retornada pela API.

### Ã°Å¸ÂÂ·Ã¯Â¸Â Tipos de usuÃƒÂ¡rio

| MÃƒÂ©todo | Endpoint | DescriÃƒÂ§ÃƒÂ£o |
|---|---|---|
| `POST` | `/api/v1/user-types` | Criar tipo de usuÃƒÂ¡rio |
| `GET` | `/api/v1/user-types` | Listar tipos |
| `GET` | `/api/v1/user-types/{id}` | Buscar tipo por ID |
| `PUT` | `/api/v1/user-types/{id}` | Atualizar tipo |
| `DELETE` | `/api/v1/user-types/{id}` | Excluir tipo |

### Ã°Å¸â€˜Â¥ UsuÃƒÂ¡rios

| MÃƒÂ©todo | Endpoint | DescriÃƒÂ§ÃƒÂ£o |
|---|---|---|
| `POST` | `/api/v1/users` | Criar usuÃƒÂ¡rio |
| `GET` | `/api/v1/users` | Listar usuÃƒÂ¡rios |
| `GET` | `/api/v1/users/{id}` | Buscar usuÃƒÂ¡rio por ID |
| `GET` | `/api/v1/users/search?name=...` | Buscar por nome |
| `PUT` | `/api/v1/users/{id}` | Atualizar usuÃƒÂ¡rio sem alterar senha |
| `PATCH` | `/api/v1/users/{id}/password` | Alterar senha |
| `DELETE` | `/api/v1/users/{id}` | Excluir usuÃƒÂ¡rio |

### Ã°Å¸ÂÂ´ Restaurantes

| MÃƒÂ©todo | Endpoint | DescriÃƒÂ§ÃƒÂ£o |
|---|---|---|
| `POST` | `/api/v1/restaurants` | Criar restaurante |
| `GET` | `/api/v1/restaurants` | Listar restaurantes |
| `GET` | `/api/v1/restaurants/{id}` | Buscar restaurante por ID |
| `PUT` | `/api/v1/restaurants/{id}` | Atualizar restaurante |
| `DELETE` | `/api/v1/restaurants/{id}` | Excluir restaurante |

### Ã°Å¸ÂÂ Itens de cardÃƒÂ¡pio

| MÃƒÂ©todo | Endpoint | DescriÃƒÂ§ÃƒÂ£o |
|---|---|---|
| `POST` | `/api/v1/menu-items` | Criar item |
| `GET` | `/api/v1/menu-items` | Listar todos os itens |
| `GET` | `/api/v1/menu-items?restaurantId={id}` | Filtrar por restaurante |
| `GET` | `/api/v1/menu-items/{id}` | Buscar item por ID |
| `PUT` | `/api/v1/menu-items/{id}` | Atualizar item |
| `DELETE` | `/api/v1/menu-items/{id}` | Excluir item |

### CÃƒÂ³digos HTTP

| CÃƒÂ³digo | Uso |
|---|---|
| `200 OK` | Consulta ou atualizaÃƒÂ§ÃƒÂ£o realizada |
| `201 Created` | Recurso criado |
| `204 No Content` | Senha alterada ou recurso excluÃƒÂ­do |
| `400 Bad Request` | Payload invÃƒÂ¡lido |
| `404 Not Found` | Recurso inexistente |
| `409 Conflict` | Duplicidade ou vÃƒÂ­nculo impeditivo |

## Ã°Å¸â€”â€žÃ¯Â¸Â Banco de dados

### ConexÃƒÂ£o padrÃƒÂ£o

```text
URL: jdbc:postgresql://localhost:5432/restaurant_db
UsuÃƒÂ¡rio: app
Senha: definida no arquivo .env
```

### Tabelas

- `user_types`: tipos de usuÃƒÂ¡rio.
- `users`: usuÃƒÂ¡rios e endereÃƒÂ§o.
- `restaurants`: restaurantes e seus donos.
- `menu_items`: itens do cardÃƒÂ¡pio.
- `flyway_schema_history`: histÃƒÂ³rico de migrations.

### Migrations

As migrations sÃƒÂ£o gerenciadas pelo Flyway e ficam em:

```text
src/main/resources/db/migration/
```

VersÃƒÂ£o implementada:

- `V1__create_initial_schema.sql`: cria tabelas, relacionamentos, ÃƒÂ­ndices e
  restriÃƒÂ§ÃƒÂµes iniciais.

## Ã°Å¸â€œÅ¡ Exemplos de uso

### 1Ã¯Â¸ÂÃ¢Æ’Â£ Criar um tipo de usuÃƒÂ¡rio

```http
POST /api/v1/user-types
Content-Type: application/json
```

```json
{
  "name": "Dono de Restaurante"
}
```

Resposta `201 Created`:

```json
{
  "id": 1,
  "name": "Dono de Restaurante"
}
```

### 2Ã¯Â¸ÂÃ¢Æ’Â£ Criar um usuÃƒÂ¡rio

```http
POST /api/v1/users
Content-Type: application/json
```

```json
{
  "name": "Maria Silva",
  "email": "maria@example.com",
  "username": "maria",
  "password": "Senha123",
  "street": "Rua A",
  "number": "10",
  "city": "SÃƒÂ£o Paulo",
  "state": "SP",
  "zipCode": "01001-000",
  "userTypeId": 1
}
```

Resposta `201 Created`:

```json
{
  "id": 1,
  "name": "Maria Silva",
  "email": "maria@example.com",
  "username": "maria",
  "street": "Rua A",
  "number": "10",
  "city": "SÃƒÂ£o Paulo",
  "state": "SP",
  "zipCode": "01001-000",
  "userType": {
    "id": 1,
    "name": "Dono de Restaurante"
  },
  "updatedAt": "2026-06-14T18:25:57Z"
}
```

### 3Ã¯Â¸ÂÃ¢Æ’Â£ Buscar usuÃƒÂ¡rios por nome

```http
GET /api/v1/users/search?name=RIA
```

A busca aceita qualquer trecho do nome e ignora diferenÃƒÂ§as de caixa. Por
exemplo, `RIA` encontra `Maria Silva`.

Resposta `200 OK`:

```json
[
  {
    "id": 1,
    "name": "Maria Silva",
    "email": "maria@example.com",
    "username": "maria"
  }
]
```

Quando nÃƒÂ£o houver resultados:

```json
[]
```

### 4Ã¯Â¸ÂÃ¢Æ’Â£ Criar um restaurante

```http
POST /api/v1/restaurants
Content-Type: application/json
```

```json
{
  "name": "Cantina FIAP",
  "address": "Avenida Paulista, 1000",
  "cuisineType": "Italiana",
  "openingHours": "11:00-23:00",
  "ownerId": 1
}
```

### 5Ã¯Â¸ÂÃ¢Æ’Â£ Criar um item de cardÃƒÂ¡pio

```http
POST /api/v1/menu-items
Content-Type: application/json
```

```json
{
  "restaurantId": 1,
  "name": "Lasanha",
  "description": "Lasanha ÃƒÂ  bolonhesa",
  "price": 39.90,
  "dineInOnly": false,
  "photoPath": "/images/lasanha.jpg"
}
```

### 6Ã¯Â¸ÂÃ¢Æ’Â£ Alterar senha

```http
PATCH /api/v1/users/1/password
Content-Type: application/json
```

```json
{
  "password": "NovaSenha123"
}
```

Resposta: `204 No Content`.

## Ã¢ÂÅ’ Erros comuns

### E-mail duplicado (`409 Conflict`)

```json
{
  "timestamp": "2026-06-14T18:30:00Z",
  "status": 409,
  "error": "Conflict",
  "message": "E-mail jÃƒÂ¡ cadastrado",
  "path": "/api/v1/users",
  "fields": {}
}
```

### Recurso nÃƒÂ£o encontrado (`404 Not Found`)

```json
{
  "timestamp": "2026-06-14T18:30:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "UsuÃƒÂ¡rio nÃƒÂ£o encontrado: 999",
  "path": "/api/v1/users/999",
  "fields": {}
}
```

### ValidaÃƒÂ§ÃƒÂ£o falhou (`400 Bad Request`)

```json
{
  "timestamp": "2026-06-14T18:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Payload invÃƒÂ¡lido",
  "path": "/api/v1/users",
  "fields": {
    "email": "must be a well-formed email address",
    "password": "size must be between 8 and 100"
  }
}
```

### ExclusÃƒÂ£o bloqueada (`409 Conflict`)

Um usuÃƒÂ¡rio que possui restaurante ou um restaurante que possui itens de
cardÃƒÂ¡pio nÃƒÂ£o pode ser excluÃƒÂ­do antes dos recursos dependentes.

## Ã°Å¸â€œâ€“ DocumentaÃƒÂ§ÃƒÂ£o Swagger

### Acessar Swagger UI

ApÃƒÂ³s iniciar a aplicaÃƒÂ§ÃƒÂ£o, acesse:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

### CaracterÃƒÂ­sticas da documentaÃƒÂ§ÃƒÂ£o

- Ã¢Å“â€¦ Endpoints gerados a partir dos controllers.
- Ã¢Å“â€¦ Contratos de requisiÃƒÂ§ÃƒÂ£o e resposta.
- Ã¢Å“â€¦ CÃƒÂ³digos de status HTTP.
- Ã¢Å“â€¦ Teste interativo das operaÃƒÂ§ÃƒÂµes.

## Ã°Å¸Â§Âª Testando a aplicaÃƒÂ§ÃƒÂ£o

### Testes automatizados

```bash
./mvnw clean verify
```

O comando executa:

- Testes unitÃƒÂ¡rios de serviÃƒÂ§o.
- Testes da query de busca por nome.
- Testes HTTP com MockMvc.
- Fluxos de integraÃƒÂ§ÃƒÂ£o dos CRUDs.
- VerificaÃƒÂ§ÃƒÂ£o automÃƒÂ¡tica de cobertura mÃƒÂ­nima de 80%.

O relatÃƒÂ³rio JaCoCo ÃƒÂ© gerado em:

```text
target/site/jacoco/index.html
```

A suÃƒÂ­te atual cobre aproximadamente 99% das instruÃƒÂ§ÃƒÂµes.

### Com Postman

Importe:

```text
docs/postman/gestao-restaurante.postman_collection.json
docs/postman/gestao-restaurante-local.postman_environment.json
```

Selecione o environment **GestÃƒÂ£o de Restaurantes - Local** antes de executar
as requisiÃƒÂ§ÃƒÂµes. A collection utiliza:

- `{{base_url}}`: URL da aplicaÃƒÂ§ÃƒÂ£o.
- `{{token}}`: JWT preenchido pela requisição `POST /api/v1/users/login` da collection.
- IDs de tipos, usuÃƒÂ¡rios, restaurantes e itens.
- Exemplos de sucesso e erro em cada operaÃƒÂ§ÃƒÂ£o aplicÃƒÂ¡vel.

Execute as pastas na ordem apresentada. As requisiÃƒÂ§ÃƒÂµes de criaÃƒÂ§ÃƒÂ£o armazenam
automaticamente os IDs necessÃƒÂ¡rios para as etapas seguintes.

### Com cURL

```bash
curl -X POST http://localhost:8080/api/v1/user-types \
  -H "Content-Type: application/json" \
  -d '{"name":"Cliente"}'
```

```bash
curl "http://localhost:8080/api/v1/users/search?name=maria"
```

## Ã°Å¸â€â€ž Pipeline CI e fluxo de branches

O workflow [`.github/workflows/ci.yml`](.github/workflows/ci.yml) executa os
testes e abre automaticamente uma pull request para `develop` quando um
colaborador faz push em uma branch monitorada.

O detalhamento arquitetural e operacional tambÃƒÂ©m estÃƒÂ¡ disponÃƒÂ­vel em
[`Relatorio_Tecnico.html`](Relatorio_Tecnico.html).

### Branches monitoradas

| Prefixo | Uso |
|---|---|
| `feature/**` | Novas funcionalidades |
| `bugfix/**` | CorreÃƒÂ§ÃƒÂµes de defeitos |
| `hotfix/**` | CorreÃƒÂ§ÃƒÂµes urgentes |
| `chore/**` | ManutenÃƒÂ§ÃƒÂ£o, documentaÃƒÂ§ÃƒÂ£o e infraestrutura |
| `refactor/**` | RefatoraÃƒÂ§ÃƒÂµes sem mudanÃƒÂ§a funcional |

Pull requests abertas para `develop` ou `main` tambÃƒÂ©m executam a pipeline.
O workflow pode ser iniciado manualmente pela opÃƒÂ§ÃƒÂ£o **Run workflow** do GitHub
Actions.

### Jobs executados

1. **Build, tests and coverage**
   - Configura o JDK 17.
   - Executa `./mvnw clean verify -B`.
   - Roda testes unitÃƒÂ¡rios e de integraÃƒÂ§ÃƒÂ£o.
   - Exige cobertura JaCoCo por instruÃƒÂ§ÃƒÂµes maior ou igual a 80%.
   - Publica os relatÃƒÂ³rios JaCoCo e Surefire como artefatos.
2. **SonarCloud analysis**
   - Executa depois dos testes.
   - Analisa cobertura, qualidade e vulnerabilidades quando configurado.
   - Sem configuraÃƒÂ§ÃƒÂ£o do SonarCloud, registra um aviso e nÃƒÂ£o bloqueia o fluxo.
3. **Create PR to develop**
   - Executa somente em pushes de branches monitoradas.
   - Requer sucesso nos testes e no job do SonarCloud.
   - Cria `develop` a partir de `main` quando essa branch ainda nÃƒÂ£o existe.
   - Evita criar PR duplicada para a mesma branch.
   - Abre a PR da branch do colaborador para `develop`.

Se compilaÃƒÂ§ÃƒÂ£o, testes ou cobertura falharem, a PR automÃƒÂ¡tica nÃƒÂ£o serÃƒÂ¡ criada.
Novos pushes na mesma branch atualizam a PR existente e executam novamente os
checks.

### Fluxo de trabalho dos colaboradores

Atualize a branch de desenvolvimento:

```bash
git switch develop
git pull origin develop
```

Crie uma branch:

```bash
git switch -c feature/nome-da-funcionalidade
```

ApÃƒÂ³s implementar e testar:

```bash
git add <arquivos>
git commit -m "feat: descreve a funcionalidade"
git push -u origin feature/nome-da-funcionalidade
```

O push inicia a pipeline. ApÃƒÂ³s a aprovaÃƒÂ§ÃƒÂ£o dos checks, o GitHub Actions abre a
PR para `develop`.

### ConfiguraÃƒÂ§ÃƒÂ£o obrigatÃƒÂ³ria do repositÃƒÂ³rio

Um administrador deve configurar no GitHub:

1. Acesse **Settings > Actions > General**.
2. Em **Workflow permissions**, selecione **Read and write permissions**.
3. Marque **Allow GitHub Actions to create and approve pull requests**.
4. Crie rulesets ou regras de proteÃƒÂ§ÃƒÂ£o para `develop` e `main`.
5. Exija pull request antes do merge.
6. Exija o status check **Build, tests and coverage**.
7. Bloqueie push direto em `develop` e `main`.
8. Defina ao menos uma aprovaÃƒÂ§ÃƒÂ£o, se desejado pelo grupo.

Os rulesets prontos devem ser importados separadamente:

```text
.github/rulesets/develop.json
.github/rulesets/main.json
```

Na pÃƒÂ¡gina **Settings > Rules > Rulesets**, use **Import a ruleset** uma vez
para cada arquivo. A interface do GitHub nÃƒÂ£o aceita um ÃƒÂºnico JSON contendo
vÃƒÂ¡rios rulesets.

O workflow tem permissÃƒÂ£o para criar `develop` automaticamente, mas a criaÃƒÂ§ÃƒÂ£o
manual inicial tambÃƒÂ©m pode ser feita com:

```bash
git switch main
git pull origin main
git switch -c develop
git push -u origin develop
```

### SonarCloud opcional

Para ativar a anÃƒÂ¡lise, configure:

| Local | Nome | DescriÃƒÂ§ÃƒÂ£o |
|---|---|---|
| Actions secret | `SONAR_TOKEN` | Token gerado no SonarCloud |
| Actions variable | `SONAR_PROJECT_KEY` | Chave do projeto |
| Actions variable | `SONAR_ORGANIZATION` | OrganizaÃƒÂ§ÃƒÂ£o do SonarCloud |

Esses valores ficam em **Settings > Secrets and variables > Actions**.

## Ã°Å¸â€ºÂ Ã¯Â¸Â Desenvolvimento

### Construir o projeto

```bash
./mvnw clean package
```

### Executar testes

```bash
./mvnw test
```

### Validar testes e cobertura

```bash
./mvnw clean verify
```

### Gerar e executar o JAR

```bash
./mvnw clean package -DskipTests
java -jar target/gestao-restaurante-0.0.1-SNAPSHOT.jar
```

## Ã°Å¸â€œâ€¹ ValidaÃƒÂ§ÃƒÂµes implementadas

### UsuÃƒÂ¡rio

| Campo | ValidaÃƒÂ§ÃƒÂ£o | Exemplo |
|---|---|---|
| Nome | ObrigatÃƒÂ³rio, atÃƒÂ© 150 caracteres | `"Maria Silva"` |
| E-mail | Formato vÃƒÂ¡lido, ÃƒÂºnico, atÃƒÂ© 180 caracteres | `"maria@example.com"` |
| Login | ObrigatÃƒÂ³rio e ÃƒÂºnico, atÃƒÂ© 80 caracteres | `"maria"` |
| Senha | Entre 8 e 100 caracteres | `"Senha123"` |
| Rua | ObrigatÃƒÂ³ria, atÃƒÂ© 120 caracteres | `"Rua A"` |
| NÃƒÂºmero | ObrigatÃƒÂ³rio, atÃƒÂ© 20 caracteres | `"10"` |
| Cidade | ObrigatÃƒÂ³ria, atÃƒÂ© 80 caracteres | `"SÃƒÂ£o Paulo"` |
| Estado | Exatamente duas letras | `"SP"` |
| CEP | Formato `XXXXX-XXX` ou `XXXXXXXX` | `"01001-000"` |
| Tipo | ID de um tipo de usuÃƒÂ¡rio existente | `1` |

### Restaurante

| Campo | ValidaÃƒÂ§ÃƒÂ£o | Exemplo |
|---|---|---|
| Nome | ObrigatÃƒÂ³rio, atÃƒÂ© 150 caracteres | `"Cantina FIAP"` |
| EndereÃƒÂ§o | ObrigatÃƒÂ³rio, atÃƒÂ© 255 caracteres | `"Avenida Paulista, 1000"` |
| Cozinha | ObrigatÃƒÂ³ria, atÃƒÂ© 100 caracteres | `"Italiana"` |
| HorÃƒÂ¡rio | ObrigatÃƒÂ³rio, atÃƒÂ© 120 caracteres | `"11:00-23:00"` |
| Dono | ID de um usuÃƒÂ¡rio existente | `1` |

### Item de cardÃƒÂ¡pio

| Campo | ValidaÃƒÂ§ÃƒÂ£o | Exemplo |
|---|---|---|
| Restaurante | ID de um restaurante existente | `1` |
| Nome | ObrigatÃƒÂ³rio, atÃƒÂ© 150 caracteres | `"Lasanha"` |
| DescriÃƒÂ§ÃƒÂ£o | ObrigatÃƒÂ³ria, atÃƒÂ© 500 caracteres | `"Lasanha ÃƒÂ  bolonhesa"` |
| PreÃƒÂ§o | Valor maior ou igual a zero, duas casas decimais | `39.90` |
| Apenas local | Valor booleano | `false` |
| Foto | Caminho obrigatÃƒÂ³rio, atÃƒÂ© 500 caracteres | `"/images/lasanha.jpg"` |

## Ã°Å¸â€œÅ¾ Suporte e problemas

### Porta 8080 em uso

Linux:

```bash
lsof -i :8080
```

Windows:

```powershell
netstat -ano | findstr :8080
```

TambÃƒÂ©m ÃƒÂ© possÃƒÂ­vel alterar `APP_PORT` no arquivo `.env`.

### Banco nÃƒÂ£o conecta

```bash
docker compose ps
docker compose logs db
docker compose exec db pg_isready
```

### Ver logs da aplicaÃƒÂ§ÃƒÂ£o

```bash
docker compose logs -f app
```

### Limpar tudo e reiniciar

> Este comando remove o volume e todos os dados locais.

```bash
docker compose down -v
docker compose up --build
```

## Ã°Å¸â€œâ€ž LicenÃƒÂ§a

Este projeto foi desenvolvido para a disciplina de Tech Challenge - FIAP.

## Ã°Å¸â€˜Â¨Ã¢â‚¬ÂÃ°Å¸â€™Â» Desenvolvedores

- Wesley de Andrade
- Juan Pablo Ruiz de Souza
- Rafael Romanini
- JoÃƒÂ£o Vitor Silva
