# Smartup Service Desk Platform

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

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details