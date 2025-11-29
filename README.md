# Smartup Service Desk Platform

[![CI](https://github.com/qahhor/FREE-SRVICEDESK2/actions/workflows/ci.yml/badge.svg)](https://github.com/qahhor/FREE-SRVICEDESK2/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java 17](https://img.shields.io/badge/Java-17-blue.svg)](https://adoptium.net/)
[![Spring Boot 3.2](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)

An Open-Source Service Desk Platform built with a modular monolith/microservices architecture.

## Tech Stack

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA** - Database persistence
- **Spring Data Redis** - Caching
- **PostgreSQL** - Primary database
- **Maven** - Build tool and dependency management

### Infrastructure
- **Docker & Docker Compose** - Containerization
- **PostgreSQL 15** - Relational database
- **Redis** - Caching layer
- **Elasticsearch 8.11** - Search and analytics (optional)

### Frontend (Planned)
- **Angular 17+**

## Project Structure

```
├── backend/
│   ├── pom.xml                 # Parent POM (multi-module Maven project)
│   ├── common-lib/             # Shared utilities and common classes
│   │   └── pom.xml
│   ├── ticket-service/         # Ticket management microservice (port 8081)
│   │   ├── pom.xml
│   │   └── src/
│   │       └── main/
│   │           ├── java/       # Java source files
│   │           └── resources/  # Configuration files
│   └── ai-service/             # AI and LLM integration service (port 8084)
│       ├── pom.xml
│       ├── README.md           # AI service documentation
│       └── src/
│           └── main/
│               ├── java/       # Java source files
│               └── resources/  # Configuration files and OpenAPI spec
├── infrastructure/
│   └── docker-compose.yml      # Docker services configuration
└── README.md
```

## Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven 3.8+**
- **Docker** and **Docker Compose**

### Running Infrastructure Services

1. Navigate to the infrastructure directory:
   ```bash
   cd infrastructure
   ```

2. Start the required services (PostgreSQL, Redis, Elasticsearch):
   ```bash
   docker-compose up -d
   ```

3. Verify services are running:
   ```bash
   docker-compose ps
   ```

### Building the Backend

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Build all modules:
   ```bash
   mvn clean install
   ```

### Running the Ticket Service

1. After building, run the ticket service:
   ```bash
   cd backend/ticket-service
   mvn spring-boot:run
   ```

2. For development with SQL logging and auto-DDL updates, use the dev profile:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

3. The service will be available at `http://localhost:8081`

### Running the AI Service

1. After building, run the AI service:
   ```bash
   cd backend/ai-service
   mvn spring-boot:run
   ```

2. The service will be available at `http://localhost:8084`

3. Access Swagger UI documentation at: `http://localhost:8084/swagger-ui/index.html`

4. By default, the mock AI provider is used. To enable OpenAI provider:
   ```bash
   export OPENAI_API_KEY=your-api-key-here
   mvn spring-boot:run
   ```

See `backend/ai-service/README.md` for more details.

## Modules

### common-lib
Shared utilities, DTOs, and common classes used across all services.

### ticket-service
Core service for managing support tickets, including:
- Ticket creation and management
- Ticket status tracking
- Ticket assignment
- AI-powered ticket classification (via ai-service integration)

**Port:** 8081

### ai-service
AI and LLM integration service for text classification and summarization:
- Text classification (categorize ticket descriptions)
- Text summarization
- Supports mock provider (development) and OpenAI provider (production)
- OpenAPI/Swagger documentation

**Port:** 8084
**Swagger UI:** http://localhost:8084/swagger-ui/index.html

## Configuration

The ticket-service configuration is in `backend/ticket-service/src/main/resources/application.yml`.

Default database connection settings:
- **Host:** localhost
- **Port:** 5432
- **Database:** servicedesk
- **Username:** servicedesk
- **Password:** servicedesk

### AI Provider Configuration

The AI service supports two providers:

| Provider | Description | Configuration |
|----------|-------------|---------------|
| Mock | Default, deterministic responses for development | No configuration needed |
| OpenAI | Production-ready with real AI capabilities | Set `OPENAI_API_KEY` environment variable |

Environment variables for OpenAI:

```bash
export OPENAI_API_KEY=your-api-key-here
export OPENAI_MODEL=gpt-4o          # Optional, default: gpt-4o
export OPENAI_TIMEOUT=30            # Optional, default: 30 seconds
```

## Testing

Run all tests:

```bash
cd backend
mvn test
```

Run tests for specific module:

```bash
mvn test -pl ai-service -am
mvn test -pl ticket-service -am
```

See [Testing Guide](docs/TESTING.md) for more details.

## Documentation

- [Installation Guide](docs/INSTALLATION.md) - Detailed setup instructions
- [Architecture Overview](docs/ARCHITECTURE.md) - System design and components
- [Testing Guide](docs/TESTING.md) - How to run and write tests
- [Contributing Guide](docs/CONTRIBUTING.md) - How to contribute

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.