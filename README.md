# 🍽️ Gestão de Restaurantes API - Tech Challenge Fase 2

Sistema de gestão para restaurantes desenvolvido como parte do Tech Challenge
FIAP. O backend permite gerenciar tipos de usuário, usuários, restaurantes e
itens de cardápio, com validação, tratamento de erros, documentação Swagger,
testes automatizados e infraestrutura Docker.

## 📋 Índice

- [Características](#-características)
- [Pré-requisitos](#-pré-requisitos)
- [Execução rápida com Docker](#-execução-rápida-com-docker)
- [Execução local](#-execução-local)
- [Estrutura do projeto](#-estrutura-do-projeto)
- [Endpoints disponíveis](#-endpoints-disponíveis)
- [Banco de dados](#️-banco-de-dados)
- [Exemplos de uso](#-exemplos-de-uso)
- [Erros comuns](#-erros-comuns)
- [Documentação Swagger](#-documentação-swagger)
- [Testando a aplicação](#-testando-a-aplicação)
- [Validações implementadas](#-validações-implementadas)
- [Suporte e problemas](#-suporte-e-problemas)

## ✨ Características

### Funcionalidades principais

- ✅ Gestão de tipos de usuário: cadastro, consulta, atualização e exclusão.
- ✅ Gestão de usuários: CRUD, alteração de senha e associação com tipo.
- ✅ Busca por nome: parcial e sem diferenciação entre maiúsculas e minúsculas.
- ✅ Gestão de restaurantes: associação obrigatória com um usuário responsável.
- ✅ Gestão de cardápio: itens associados ao restaurante.
- ✅ Validações robustas com Jakarta Validation.
- ✅ Erros padronizados para payload inválido, recurso inexistente e conflito.
- ✅ API versionada sob `/api/v1`.
- ✅ Documentação interativa com Swagger/OpenAPI.
- ✅ Migrations de banco de dados com Flyway.
- ✅ Testes unitários e de integração com cobertura superior a 80%.
- ✅ Execução integrada com Docker Compose.

### Stack tecnológico

- Java 21
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

## 🔧 Pré-requisitos

### Instalação local

- Java 21+
- Maven 3.9+ ou Maven Wrapper
- PostgreSQL 16+
- Git

### Com Docker (recomendado)

- Docker 20.10+
- Docker Compose 2+

## 🚀 Execução rápida com Docker

### 1. Clonar o repositório

```bash
git clone <url-do-repositorio>
cd gestao-restaurante
```

### 2. Configurar as variáveis de ambiente

```bash
cp .env.example .env
```

Variáveis disponíveis:

| Variável | Descrição | Exemplo |
|---|---|---|
| `POSTGRES_DB` | Nome do banco | `restaurant_db` |
| `POSTGRES_USER` | Usuário do banco | `app` |
| `POSTGRES_PASSWORD` | Senha do banco | `change-me` |
| `POSTGRES_PORT` | Porta externa do PostgreSQL | `5432` |
| `APP_PORT` | Porta externa da API | `8080` |

### 3. Iniciar os serviços

```bash
docker compose up --build
```

O Docker Compose irá:

- Construir a aplicação em Java 21 com um Dockerfile multi-stage.
- Criar e iniciar o PostgreSQL 16.
- Executar o healthcheck do banco.
- Iniciar a aplicação somente quando o banco estiver saudável.
- Aplicar as migrations do Flyway.
- Persistir os dados no volume `postgres_data`.

### 4. Acessar a aplicação

- API base: `http://localhost:8080/api/v1`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

### 5. Parar os serviços

```bash
docker compose down
```

Para também remover os dados persistidos:

```bash
docker compose down -v
```

## 💻 Execução local

Configure a conexão com o PostgreSQL:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/restaurant_db
export SPRING_DATASOURCE_USERNAME=app
export SPRING_DATASOURCE_PASSWORD=secret
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

## 📁 Estrutura do projeto

```text
gestao-restaurante/
├── src/
│   ├── main/
│   │   ├── java/com/fiap/gestaorestaurante/
│   │   │   ├── Application.java
│   │   │   ├── domain/
│   │   │   │   └── model/                     # Entidades do domínio
│   │   │   ├── application/
│   │   │   │   └── service/                   # Casos de uso e regras
│   │   │   └── infrastructure/
│   │   │       ├── persistence/               # Repositórios JPA
│   │   │       └── web/
│   │   │           ├── dto/                   # Contratos HTTP
│   │   │           ├── exception/             # Erros centralizados
│   │   │           └── *Controller.java       # Endpoints REST
│   │   └── resources/
│   │       ├── application.yaml
│   │       └── db/migration/
│   │           └── V1__create_initial_schema.sql
│   └── test/
│       ├── java/com/fiap/gestaorestaurante/
│       │   ├── application/service/
│       │   ├── infrastructure/persistence/
│       │   └── infrastructure/web/
│       └── resources/application.yaml
├── docs/postman/
│   └── gestao-restaurante.postman_collection.json
├── .env.example
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

### Arquitetura

O projeto adota uma separação inspirada em Clean Architecture:

- **Domain:** entidades e comportamentos centrais.
- **Application:** casos de uso e regras de negócio.
- **Infrastructure:** persistência JPA, API HTTP e configurações técnicas.

Relacionamentos principais:

```text
UserType 1 ─── N User
User     1 ─── N Restaurant
Restaurant 1 ─── N MenuItem
```

## 📡 Endpoints disponíveis

Todos os endpoints são públicos nesta fase. A senha é armazenada com BCrypt e
nunca é retornada pela API.

### 🏷️ Tipos de usuário

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/v1/user-types` | Criar tipo de usuário |
| `GET` | `/api/v1/user-types` | Listar tipos |
| `GET` | `/api/v1/user-types/{id}` | Buscar tipo por ID |
| `PUT` | `/api/v1/user-types/{id}` | Atualizar tipo |
| `DELETE` | `/api/v1/user-types/{id}` | Excluir tipo |

### 👥 Usuários

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/v1/users` | Criar usuário |
| `GET` | `/api/v1/users` | Listar usuários |
| `GET` | `/api/v1/users/{id}` | Buscar usuário por ID |
| `GET` | `/api/v1/users/search?name=...` | Buscar por nome |
| `PUT` | `/api/v1/users/{id}` | Atualizar usuário sem alterar senha |
| `PATCH` | `/api/v1/users/{id}/password` | Alterar senha |
| `DELETE` | `/api/v1/users/{id}` | Excluir usuário |

### 🍴 Restaurantes

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/v1/restaurants` | Criar restaurante |
| `GET` | `/api/v1/restaurants` | Listar restaurantes |
| `GET` | `/api/v1/restaurants/{id}` | Buscar restaurante por ID |
| `PUT` | `/api/v1/restaurants/{id}` | Atualizar restaurante |
| `DELETE` | `/api/v1/restaurants/{id}` | Excluir restaurante |

### 🍝 Itens de cardápio

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/v1/menu-items` | Criar item |
| `GET` | `/api/v1/menu-items` | Listar todos os itens |
| `GET` | `/api/v1/menu-items?restaurantId={id}` | Filtrar por restaurante |
| `GET` | `/api/v1/menu-items/{id}` | Buscar item por ID |
| `PUT` | `/api/v1/menu-items/{id}` | Atualizar item |
| `DELETE` | `/api/v1/menu-items/{id}` | Excluir item |

### Códigos HTTP

| Código | Uso |
|---|---|
| `200 OK` | Consulta ou atualização realizada |
| `201 Created` | Recurso criado |
| `204 No Content` | Senha alterada ou recurso excluído |
| `400 Bad Request` | Payload inválido |
| `404 Not Found` | Recurso inexistente |
| `409 Conflict` | Duplicidade ou vínculo impeditivo |

## 🗄️ Banco de dados

### Conexão padrão

```text
URL: jdbc:postgresql://localhost:5432/restaurant_db
Usuário: app
Senha: definida no arquivo .env
```

### Tabelas

- `user_types`: tipos de usuário.
- `users`: usuários e endereço.
- `restaurants`: restaurantes e seus donos.
- `menu_items`: itens do cardápio.
- `flyway_schema_history`: histórico de migrations.

### Migrations

As migrations são gerenciadas pelo Flyway e ficam em:

```text
src/main/resources/db/migration/
```

Versão implementada:

- `V1__create_initial_schema.sql`: cria tabelas, relacionamentos, índices e
  restrições iniciais.

## 📚 Exemplos de uso

### 1️⃣ Criar um tipo de usuário

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

### 2️⃣ Criar um usuário

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
  "city": "São Paulo",
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
  "city": "São Paulo",
  "state": "SP",
  "zipCode": "01001-000",
  "userType": {
    "id": 1,
    "name": "Dono de Restaurante"
  },
  "updatedAt": "2026-06-14T18:25:57Z"
}
```

### 3️⃣ Buscar usuários por nome

```http
GET /api/v1/users/search?name=RIA
```

A busca aceita qualquer trecho do nome e ignora diferenças de caixa. Por
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

Quando não houver resultados:

```json
[]
```

### 4️⃣ Criar um restaurante

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

### 5️⃣ Criar um item de cardápio

```http
POST /api/v1/menu-items
Content-Type: application/json
```

```json
{
  "restaurantId": 1,
  "name": "Lasanha",
  "description": "Lasanha à bolonhesa",
  "price": 39.90,
  "dineInOnly": false,
  "photoPath": "/images/lasanha.jpg"
}
```

### 6️⃣ Alterar senha

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

## ❌ Erros comuns

### E-mail duplicado (`409 Conflict`)

```json
{
  "timestamp": "2026-06-14T18:30:00Z",
  "status": 409,
  "error": "Conflict",
  "message": "E-mail já cadastrado",
  "path": "/api/v1/users",
  "fields": {}
}
```

### Recurso não encontrado (`404 Not Found`)

```json
{
  "timestamp": "2026-06-14T18:30:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Usuário não encontrado: 999",
  "path": "/api/v1/users/999",
  "fields": {}
}
```

### Validação falhou (`400 Bad Request`)

```json
{
  "timestamp": "2026-06-14T18:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Payload inválido",
  "path": "/api/v1/users",
  "fields": {
    "email": "must be a well-formed email address",
    "password": "size must be between 8 and 100"
  }
}
```

### Exclusão bloqueada (`409 Conflict`)

Um usuário que possui restaurante ou um restaurante que possui itens de
cardápio não pode ser excluído antes dos recursos dependentes.

## 📖 Documentação Swagger

### Acessar Swagger UI

Após iniciar a aplicação, acesse:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

### Características da documentação

- ✅ Endpoints gerados a partir dos controllers.
- ✅ Contratos de requisição e resposta.
- ✅ Códigos de status HTTP.
- ✅ Teste interativo das operações.

## 🧪 Testando a aplicação

### Testes automatizados

```bash
./mvnw clean verify
```

O comando executa:

- Testes unitários de serviço.
- Testes da query de busca por nome.
- Testes HTTP com MockMvc.
- Fluxos de integração dos CRUDs.
- Verificação automática de cobertura mínima de 80%.

O relatório JaCoCo é gerado em:

```text
target/site/jacoco/index.html
```

A suíte atual cobre aproximadamente 99% das instruções.

### Com Postman

Importe:

```text
docs/postman/gestao-restaurante.postman_collection.json
```

A collection utiliza:

- `{{base_url}}`: URL da aplicação.
- `{{token}}`: variável preparada para autenticação futura.
- IDs de tipos, usuários, restaurantes e itens.
- Exemplos de sucesso e erro em cada operação aplicável.

### Com cURL

```bash
curl -X POST http://localhost:8080/api/v1/user-types \
  -H "Content-Type: application/json" \
  -d '{"name":"Cliente"}'
```

```bash
curl "http://localhost:8080/api/v1/users/search?name=maria"
```

## 🛠️ Desenvolvimento

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

## 📋 Validações implementadas

### Usuário

| Campo | Validação | Exemplo |
|---|---|---|
| Nome | Obrigatório, até 150 caracteres | `"Maria Silva"` |
| E-mail | Formato válido, único, até 180 caracteres | `"maria@example.com"` |
| Login | Obrigatório e único, até 80 caracteres | `"maria"` |
| Senha | Entre 8 e 100 caracteres | `"Senha123"` |
| Rua | Obrigatória, até 120 caracteres | `"Rua A"` |
| Número | Obrigatório, até 20 caracteres | `"10"` |
| Cidade | Obrigatória, até 80 caracteres | `"São Paulo"` |
| Estado | Exatamente duas letras | `"SP"` |
| CEP | Formato `XXXXX-XXX` ou `XXXXXXXX` | `"01001-000"` |
| Tipo | ID de um tipo de usuário existente | `1` |

### Restaurante

| Campo | Validação | Exemplo |
|---|---|---|
| Nome | Obrigatório, até 150 caracteres | `"Cantina FIAP"` |
| Endereço | Obrigatório, até 255 caracteres | `"Avenida Paulista, 1000"` |
| Cozinha | Obrigatória, até 100 caracteres | `"Italiana"` |
| Horário | Obrigatório, até 120 caracteres | `"11:00-23:00"` |
| Dono | ID de um usuário existente | `1` |

### Item de cardápio

| Campo | Validação | Exemplo |
|---|---|---|
| Restaurante | ID de um restaurante existente | `1` |
| Nome | Obrigatório, até 150 caracteres | `"Lasanha"` |
| Descrição | Obrigatória, até 500 caracteres | `"Lasanha à bolonhesa"` |
| Preço | Valor maior ou igual a zero, duas casas decimais | `39.90` |
| Apenas local | Valor booleano | `false` |
| Foto | Caminho obrigatório, até 500 caracteres | `"/images/lasanha.jpg"` |

## 📞 Suporte e problemas

### Porta 8080 em uso

Linux:

```bash
lsof -i :8080
```

Windows:

```powershell
netstat -ano | findstr :8080
```

Também é possível alterar `APP_PORT` no arquivo `.env`.

### Banco não conecta

```bash
docker compose ps
docker compose logs db
docker compose exec db pg_isready
```

### Ver logs da aplicação

```bash
docker compose logs -f app
```

### Limpar tudo e reiniciar

> Este comando remove o volume e todos os dados locais.

```bash
docker compose down -v
docker compose up --build
```

## 📄 Licença

Este projeto foi desenvolvido para a disciplina de Tech Challenge - FIAP.

## 👨‍💻 Desenvolvedores

- Wesley de Andrade
- Juan Pablo Ruiz de Souza
- Rafael Romanini
- João Vitor Silva
