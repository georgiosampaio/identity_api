
# Identity API

REST API for user management and authentication, built with Java and Spring Boot.

## Technologies

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- JSON Web Tokens (JWT)
- Maven
- Springdoc OpenAPI / Swagger UI
- GitHub Actions

## Prerequisites

Before running the application, install:

- JDK 21
- PostgreSQL
- Git

The project includes the Maven Wrapper, so a separate Maven installation is not required.

## Database Configuration

Create a PostgreSQL database named `db_usuario`.

Configure the following environment variables on your operating system:

| Variable | Description |
|---|---|
| `DB_URL` | JDBC URL of the PostgreSQL database |
| `DB_USERNAME` | PostgreSQL username |
| `DB_PASSWORD` | PostgreSQL password |
| `JWT_SECRET` | Base64-encoded secret used to sign JWTs |

Example database URL:

```text
jdbc:postgresql://localhost:5432/db_usuario
```

Use your own database credentials. Do not commit passwords or production secrets to the repository.

`JWT_SECRET` must contain a Base64-encoded cryptographic key with at least 32 bytes of decoded data. Generate a unique secret for each environment.

## Running the Application

On Windows, open a terminal in the project root and run:

```powershell
.\mvnw.cmd spring-boot:run
```

The application starts on port `8080` by default.

Make sure PostgreSQL is running and all required environment variables are configured before starting the application.

## Running Tests

Execute the test suite with:

```powershell
.\mvnw.cmd clean verify
```

## API Documentation

When the application is running, access Swagger UI at:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI documentation is available at:

```text
http://localhost:8080/v3/api-docs
```

## Continuous Integration

GitHub Actions runs the Maven build and tests when changes are pushed to the `main` branch or when a pull request targets `main`.

The workflow is defined in:

```text
.github/workflows/maven.yml
```

The CI workflow uses a temporary PostgreSQL service and test-only credentials. These are separate from local and production credentials.

## Security

- Database credentials and JWT signing secrets must be supplied through environment variables.
- Never commit real credentials, private keys, or production secrets.
- Use a separate database and unique secrets for production.
