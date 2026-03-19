# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**VendaServicosProdutosApi** — Spring Boot REST API for a LAN House point-of-sale system. Manages sales orders, products, print services, and users with JWT authentication.

- **Stack:** Java 21, Spring Boot 3.4.6, MySQL 8, Maven
- **API docs:** `http://localhost:8080/swagger-ui.html`

## Common Commands

```bash
# Run application locally (requires MySQL on port 3306)
mvn spring-boot:run

# Run full stack with Docker
docker-compose up

# Build JAR (skip tests for Docker build)
mvn clean package -DskipTests

# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=ClassName

# Run a specific test method
mvn test -Dtest=ClassName#methodName
```

## Architecture

Layered architecture: **Controller → Service → Repository → Database**

### Package Responsibilities

- `controller/` — REST endpoints. Each controller delegates entirely to a service.
- `service/` — Business logic: order total calculations, inventory, audit records.
- `repository/` — Spring Data JPA interfaces. Complex reports use custom `@Query` (JPQL).
- `model/` — JPA entities. All extend `DomainBase`, which auto-populates `created_by`, `updated_by`, `date_created`, `last_updated` from the Spring Security context.
- `dto/` — Request DTOs (`*CreateRequestDTO`) and response DTOs (`*ResponseDTO`). API contract is separate from entities.
- `mapper/` — Manual DTO↔Entity mapping classes (no MapStruct).
- `configurations/` — Spring Security config, JWT filter, Swagger config, and `DataInitializer` (creates default admin on first boot).
- `exception/` — `RecursoNaoEncontradoException` (404) and `AuthenticationException`.

### Key Domain Concepts

- **SalesOrder / OrderItens:** An order contains line items (`OrderItens`) that can reference *either* a `Product` or a `PrintService` (differentiated by `ItemType` enum: `PRODUTO` / `SERVICO`). Item total is calculated via `@PrePersist/@PreUpdate`.
- **OrderStatus enum:** `ABERTO`, `PAGO`, etc.
- **UserType enum:** `ADMIN`, `VENDEDOR`, etc.
- **AuditLogProduct:** Detailed change history for products beyond the base audit fields.

### Security & Authentication

- **Stateless JWT** — No server sessions. Tokens stored in DB for revocation tracking.
- `JwtFilter` validates the `Authorization: Bearer <token>` header on every protected request.
- BCrypt password encoding.
- Public endpoints: `/auth/generateToken`, all Swagger paths.
- Default admin created on startup: `admin@admin.com` / `admin123`.

### Database

- MySQL 8, database name `services_lan`.
- Hibernate DDL auto: `update` (schema managed by entity definitions, no Flyway/Liquibase).
- Docker maps host port **3307** → container port 3306.

### CORS Allowed Origins

- `https://www.arenagames.net.br`, `https://arenagames.net.br`
- `http://192.168.1.90:8081`
