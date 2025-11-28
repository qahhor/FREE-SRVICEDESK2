# Service Desk Platform

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
- **Redis 7** - Caching layer
- **Elasticsearch 8.11** - Search and analytics (optional)

### Frontend (Planned)
- **Angular 17+**

## Project Structure

```
├── backend/
│   ├── pom.xml                 # Parent POM (multi-module Maven project)
│   ├── common-lib/             # Shared utilities and common classes
│   │   └── pom.xml
│   └── ticket-service/         # Ticket management microservice
│       ├── pom.xml
│       └── src/
│           └── main/
│               ├── java/       # Java source files
│               └── resources/  # Configuration files
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

2. The service will be available at `http://localhost:8080`

## Modules

### common-lib
Shared utilities, DTOs, and common classes used across all services.

### ticket-service
Core service for managing support tickets, including:
- Ticket creation and management
- Ticket status tracking
- Ticket assignment

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