# Devsu Banking API — Technical Assessment

A Spring Boot REST API for a banking system. Provides endpoints for managing clients, accounts, and transactions, with business rule enforcement, PDF/JSON reporting, PostgreSQL integration, and Docker containerization.

## Features

- **Client Management**: Full CRUD for client (persona + cliente) data.
- **Account Management**: Savings/Checking accounts linked to clients.
- **Transaction Processing**: Deposits and withdrawals with automatic balance tracking.
- **Business Rules**: Rejects transactions that would leave a negative balance ("Saldo no disponible") or exceed the $1,000 daily debit limit ("Cupo diario Excedido").
- **Reporting**: Account statement endpoint returning JSON or PDF for a given client and date range.
- **Docker Support**: Multi-stage Dockerfile + `docker-compose.yml` for full-stack deployment.
- **Testing**: Unit tests for controller layer using pure Mockito (no Spring context required).

## Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 25 | Programming Language |
| Spring Boot | 4.0.5 | Application Framework |
| PostgreSQL | 16 | Database |
| Flyway | — | Database Migrations |
| Spring Data JPA / Hibernate | — | Data Access / ORM |
| MapStruct | 1.6.3 | DTO ↔ Entity Mapping |
| Lombok | 1.18.42 | Boilerplate Reduction |
| OpenPDF | 2.0.3 | PDF Report Generation |
| spring-security-crypto | — | BCrypt Password Encoding |
| Docker | — | Containerization |

## Project Structure

```text
backend-test/
├── src/
│   ├── main/
│   │   ├── java/com/devsu/banking/
│   │   │   ├── config/             # GlobalExceptionHandler, AppConfig
│   │   │   ├── domain/
│   │   │   │   ├── dto/            # Java records (request/response DTOs)
│   │   │   │   ├── entity/         # Persona (MappedSuperclass), Cliente, Cuenta, Movimiento
│   │   │   │   ├── mapper/         # MapStruct interfaces
│   │   │   │   ├── repository/     # Spring Data JPA repositories
│   │   │   │   └── service/        # Business logic
│   │   │   └── web/
│   │   │       └── controller/     # REST controllers
│   │   └── resources/
│   │       ├── db/migration/       # Flyway SQL migrations
│   │       └── application.yaml
│   └── test/
│       ├── java/com/devsu/banking/ # Mockito unit tests
│       └── resources/
│           └── application-test.yaml
├── BaseDatos.sql                   # Schema deliverable
├── Dockerfile
└── docker-compose.yml
```

## Prerequisites

- **Java 25** JDK
- **Maven** 3.9+
- **Docker** & **Docker Compose**

## Getting Started

### 1. Run with Docker Compose (Recommended)

This builds the application image and starts it alongside PostgreSQL:

```bash
docker-compose up --build
```

The API will be available at `http://localhost:8080/api`.

### 2. Run Locally

Ensure a PostgreSQL instance is running, then set the following environment variables:

| Variable | Default | Description |
|----------|---------|-------------|
| `DB_HOST` | `localhost` | PostgreSQL host |
| `DB_PORT` | `5432` | PostgreSQL port |
| `DB_NAME` | `banking` | Database name |
| `DB_USER` | `banking` | Database username |
| `DB_PASS` | `banking` | Database password |

```bash
# PowerShell
$env:DB_HOST="localhost"; $env:DB_NAME="banking"; $env:DB_USER="banking"; $env:DB_PASS="banking"

mvn spring-boot:run
```

Flyway will run the migration automatically on startup and create all required tables.

### 3. Database Setup (standalone PostgreSQL)

To start only the database via Docker:

```bash
docker run -d --name banking-db \
  -e POSTGRES_DB=banking \
  -e POSTGRES_USER=banking \
  -e POSTGRES_PASSWORD=banking \
  -p 5432:5432 \
  postgres:16-alpine
```

Or execute `BaseDatos.sql` against an existing PostgreSQL instance.

## Testing

```bash
mvn test
```

Unit tests use pure Mockito (`@ExtendWith(MockitoExtension.class)`) and do not require a running database or Spring context.

## API Endpoints

Base path: `/api`

### Clientes — `/api/clientes`

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/clientes` | List all clients |
| `GET` | `/clientes/{id}` | Get client by ID |
| `POST` | `/clientes` | Create client |
| `PUT` | `/clientes/{id}` | Full update |
| `PATCH` | `/clientes/{id}` | Partial update |
| `DELETE` | `/clientes/{id}` | Delete client |

### Cuentas — `/api/cuentas`

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/cuentas` | List all accounts |
| `GET` | `/cuentas/{id}` | Get account by ID |
| `POST` | `/cuentas` | Create account |
| `PUT` | `/cuentas/{id}` | Full update |
| `PATCH` | `/cuentas/{id}` | Partial update |
| `DELETE` | `/cuentas/{id}` | Soft-delete (sets estado=false) |

### Movimientos — `/api/movimientos`

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/movimientos` | List all transactions |
| `GET` | `/movimientos/{id}` | Get transaction by ID |
| `POST` | `/movimientos` | Register transaction (applies business rules) |
| `PUT` | `/movimientos/{id}` | Full update |
| `DELETE` | `/movimientos/{id}` | Delete transaction |

### Reportes — `/api/reportes`

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/reportes?fecha=yyyy-MM-dd,yyyy-MM-dd&cliente={id}` | Account statement (JSON or PDF) |

Pass `Accept: application/json` for JSON or `Accept: application/pdf` for a PDF download.

## cURL Examples

### Clientes

```bash
# Create
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Jose Lema","genero":"M","edad":30,"identificacion":"1234567890","direccion":"Otavalo sn y principal","telefono":"098254785","clienteid":"joselema","contrasena":"1234","estado":true}'

# Get all
curl http://localhost:8080/api/clientes

# Get by ID
curl http://localhost:8080/api/clientes/1

# Full update
curl -X PUT http://localhost:8080/api/clientes/1 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Jose Lema","genero":"M","edad":31,"identificacion":"1234567890","direccion":"Nueva Direccion","telefono":"098000000","clienteid":"joselema","contrasena":"1234","estado":true}'

# Partial update
curl -X PATCH http://localhost:8080/api/clientes/1 \
  -H "Content-Type: application/json" \
  -d '{"telefono":"0990000000"}'

# Delete
curl -X DELETE http://localhost:8080/api/clientes/1
```

### Cuentas

```bash
# Create
curl -X POST http://localhost:8080/api/cuentas \
  -H "Content-Type: application/json" \
  -d '{"numeroCuenta":"478758","tipoCuenta":"Ahorros","saldoInicial":2000.00,"estado":true,"clienteId":1}'

# Get all
curl http://localhost:8080/api/cuentas

# Get by ID
curl http://localhost:8080/api/cuentas/1

# Update
curl -X PUT http://localhost:8080/api/cuentas/1 \
  -H "Content-Type: application/json" \
  -d '{"numeroCuenta":"478758","tipoCuenta":"Corriente","saldoInicial":2000.00,"estado":true,"clienteId":1}'

# Delete (soft)
curl -X DELETE http://localhost:8080/api/cuentas/1
```

### Movimientos

```bash
# Credit — adds to balance
curl -X POST http://localhost:8080/api/movimientos \
  -H "Content-Type: application/json" \
  -d '{"cuentaId":1,"tipoMovimiento":"CREDITO","valor":500.00}'

# Debit — subtracts from balance
curl -X POST http://localhost:8080/api/movimientos \
  -H "Content-Type: application/json" \
  -d '{"cuentaId":1,"tipoMovimiento":"DEBITO","valor":325.00}'

# Debit exceeding balance → 409 "Saldo no disponible"
curl -X POST http://localhost:8080/api/movimientos \
  -H "Content-Type: application/json" \
  -d '{"cuentaId":1,"tipoMovimiento":"DEBITO","valor":99999.00}'

# Debit exceeding daily limit → 409 "Cupo diario Excedido"
curl -X POST http://localhost:8080/api/movimientos \
  -H "Content-Type: application/json" \
  -d '{"cuentaId":1,"tipoMovimiento":"DEBITO","valor":1500.00}'

# Get all
curl http://localhost:8080/api/movimientos
```

### Reportes

```bash
# JSON report
curl "http://localhost:8080/api/reportes?fecha=2024-01-01,2024-12-31&cliente=1" \
  -H "Accept: application/json"

# PDF report — saves to reporte.pdf
curl "http://localhost:8080/api/reportes?fecha=2024-01-01,2024-12-31&cliente=1" \
  -H "Accept: application/pdf" \
  --output reporte.pdf
```

## Business Rules

| Rule | Behavior |
|------|----------|
| Insufficient balance | HTTP 409 `{ "error": "Saldo no disponible" }` |
| Daily debit limit exceeded ($1,000) | HTTP 409 `{ "error": "Cupo diario Excedido" }` |
| Resource not found | HTTP 404 |
| Validation error | HTTP 400 with field-level error details |

## Docker

```bash
# Build image manually
docker build -t devsu/banking-api .

# Run against an existing DB
docker run -p 8080:8080 \
  -e DB_HOST=host.docker.internal \
  -e DB_NAME=banking \
  -e DB_USER=banking \
  -e DB_PASS=banking \
  devsu/banking-api
```

## License

MIT
