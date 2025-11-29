# Architecture Overview

This document describes the architecture of the Service Desk Platform, including its components, services, and design decisions.

## System Overview

The Service Desk Platform is built as a modular monolith with microservices architecture, designed to be scalable and maintainable.

```
┌─────────────────────────────────────────────────────────────────┐
│                     Service Desk Platform                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌─────────────────┐     ┌─────────────────┐                    │
│  │  Ticket Service │────▶│   AI Service    │                    │
│  │     (8081)      │     │     (8084)      │                    │
│  └────────┬────────┘     └────────┬────────┘                    │
│           │                       │                              │
│           │                       │ (optional)                   │
│           ▼                       ▼                              │
│  ┌─────────────────┐     ┌─────────────────┐                    │
│  │   PostgreSQL    │     │    OpenAI API   │                    │
│  │     (5432)      │     │   (external)    │                    │
│  └─────────────────┘     └─────────────────┘                    │
│                                                                  │
└─────────────────────────────────────────────────────────────────┘
```

## Components

### Backend Services

#### 1. Common Library (`common-lib`)

Shared utilities and classes used across all services:

- Common DTOs
- Utility functions
- Shared constants

#### 2. Ticket Service (`ticket-service`)

Core service for managing support tickets.

**Features:**
- Create, read, update tickets
- AI-powered ticket classification
- Ticket categorization and labeling

**Technology:**
- Spring Boot 3.2
- Spring Data JPA
- PostgreSQL
- WebClient for AI service integration

**API Endpoints:**
- `POST /api/v1/tickets` - Create a new ticket
- `GET /api/v1/tickets` - List all tickets
- `GET /api/v1/tickets/{id}` - Get a specific ticket

#### 3. AI Service (`ai-service`)

AI and LLM integration service for text classification and summarization.

**Features:**
- Text classification
- Text summarization
- Pluggable AI provider architecture

**Technology:**
- Spring Boot 3.2
- Spring WebFlux (WebClient)
- OpenAPI/Swagger documentation

**API Endpoints:**
- `POST /api/v1/ai/classify` - Classify text
- `POST /api/v1/ai/summarize` - Summarize text
- `GET /swagger-ui/index.html` - API documentation

**AI Providers:**
- **MockAiProvider** - Default provider for development, returns deterministic responses
- **OpenAiProvider** - Production provider using OpenAI API

#### 4. Knowledge Service (`knowledge-service`)

Knowledge base and self-service module (planned features).

### Infrastructure

#### PostgreSQL

Primary database for storing tickets and application data.

- **Version:** 15-alpine
- **Port:** 5432
- **Database:** servicedesk

#### Redis (Optional)

Caching layer for improved performance.

- **Port:** 6379

#### Elasticsearch (Optional)

Search and analytics engine.

- **Version:** 8.11.0
- **Port:** 9200

## Design Patterns

### Provider Pattern (AI Service)

The AI service uses the provider pattern to support multiple AI backends:

```java
public interface AiProvider {
    List<String> classify(String text);
    String summarize(String text);
}
```

Implementations:
- `MockAiProvider` - For testing and development
- `OpenAiProvider` - For production with OpenAI

The provider is selected at runtime based on configuration:

```yaml
app:
  ai:
    provider: ${AI_PROVIDER:openai}
    openai:
      api-key: ${OPENAI_API_KEY:}
```

### In-Memory Store Pattern (Ticket Service)

Currently uses in-memory storage for simplicity. Can be extended to use JPA repository.

```java
private final Map<Long, Ticket> ticketStore = new ConcurrentHashMap<>();
```

## Communication Flow

### Ticket Creation with AI Classification

```
1. Client ──POST /api/v1/tickets──▶ Ticket Service
2. Ticket Service ──POST /api/v1/ai/classify──▶ AI Service
3. AI Service ──classify()──▶ AI Provider
4. AI Provider returns labels
5. Ticket Service stores ticket with labels
6. Client receives created ticket
```

## Configuration Management

### Environment Variables

Key environment variables:

| Variable | Description | Default |
|----------|-------------|---------|
| `SERVER_PORT` | Service port | 8081 (ticket), 8084 (ai) |
| `OPENAI_API_KEY` | OpenAI API key | (none) |
| `AI_PROVIDER` | AI provider to use | openai |
| `SPRING_DATASOURCE_URL` | Database URL | jdbc:postgresql://localhost:5432/servicedesk |

### Profile-Based Configuration

- **default** - Standard configuration
- **dev** - Development with verbose logging
- **test** - Testing configuration with H2 database

## Security Considerations

1. **API Keys** - Never commit API keys to source control
2. **Database** - Use secure passwords in production
3. **Network** - Services communicate internally

## Scalability

The architecture supports:

1. **Horizontal Scaling** - Each service can be scaled independently
2. **Caching** - Redis integration for frequently accessed data
3. **Async Processing** - WebFlux for non-blocking I/O

## Future Improvements

- [ ] Add authentication and authorization
- [ ] Implement ticket persistence with JPA
- [ ] Add event-driven architecture with Kafka
- [ ] Implement circuit breaker for AI service calls
- [ ] Add metrics and monitoring with Prometheus/Grafana
