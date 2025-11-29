# Installation Guide

This guide provides detailed instructions for setting up the Service Desk Platform for local development and deployment.

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17** (or higher) - [Download OpenJDK](https://adoptium.net/)
- **Maven 3.8+** - [Download Maven](https://maven.apache.org/download.cgi)
- **Docker** and **Docker Compose** - [Install Docker](https://docs.docker.com/get-docker/)
- **Git** - [Install Git](https://git-scm.com/)

## Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/qahhor/FREE-SRVICEDESK2.git
cd FREE-SRVICEDESK2
```

### 2. Start Infrastructure Services

Start PostgreSQL, Redis, and Elasticsearch using Docker Compose:

```bash
cd infrastructure
docker-compose up -d
```

Verify services are running:

```bash
docker-compose ps
```

### 3. Build the Backend

```bash
cd ../backend
mvn clean install
```

### 4. Run the Services

Start the AI Service (port 8084):

```bash
cd ai-service
mvn spring-boot:run
```

In a new terminal, start the Ticket Service (port 8081):

```bash
cd ../ticket-service
mvn spring-boot:run
```

## Detailed Setup

### Database Configuration

The default database configuration:

| Property | Value |
|----------|-------|
| Host | localhost |
| Port | 5432 |
| Database | servicedesk |
| Username | servicedesk |
| Password | servicedesk |

To customize, set environment variables before running services:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://your-host:5432/your-db
export SPRING_DATASOURCE_USERNAME=your-user
export SPRING_DATASOURCE_PASSWORD=your-password
```

### AI Service Configuration

The AI Service supports two providers:

#### Mock Provider (Default)

Used for development when no OpenAI API key is configured. Returns deterministic responses.

#### OpenAI Provider

To enable real AI capabilities:

```bash
export OPENAI_API_KEY=your-api-key-here
export OPENAI_MODEL=gpt-4o  # Optional, default is gpt-4o
```

Additional OpenAI configuration options:

| Variable | Default | Description |
|----------|---------|-------------|
| `OPENAI_API_KEY` | (none) | Your OpenAI API key |
| `OPENAI_MODEL` | gpt-4o | Model to use |
| `OPENAI_TIMEOUT` | 30 | Request timeout in seconds |
| `OPENAI_MAX_TOKENS` | 500 | Maximum tokens in response |
| `OPENAI_TEMPERATURE` | 0.3 | Response temperature (0.0-2.0) |

### Running with Docker

Build Docker images for the services:

```bash
cd backend/ai-service
mvn spring-boot:build-image

cd ../ticket-service
mvn spring-boot:build-image
```

## Development Profiles

### Development Profile

For enhanced logging and auto-DDL:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Troubleshooting

### Database Connection Issues

1. Ensure PostgreSQL is running: `docker-compose ps`
2. Check database logs: `docker-compose logs postgres`
3. Verify connection settings in `application.yml`

### Maven Build Failures

1. Ensure Java 17 is in PATH: `java -version`
2. Clear Maven cache: `mvn clean install -U`
3. Check for conflicting dependencies

### Port Conflicts

If ports are already in use:

```bash
# Change AI Service port
export SERVER_PORT=8085

# Change Ticket Service port
export SERVER_PORT=8082
```

## Next Steps

- Review the [Architecture Guide](ARCHITECTURE.md)
- Learn about [Testing](TESTING.md)
- Read the [Contributing Guide](CONTRIBUTING.md)
